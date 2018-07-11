package club.biggerm.asionbo.wanandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import club.biggerm.asionbo.wanandroid.adapter.ArticleTitleAdapter
import club.biggerm.asionbo.wanandroid.model.Article
import club.biggerm.asionbo.wanandroid.model.Banner
import club.biggerm.asionbo.wanandroid.model.Event
import club.biggerm.asionbo.wanandroid.network.OpenApiService
import club.biggerm.asionbo.wanandroid.network.WebDataSource
import club.biggerm.asionbo.wanandroid.utils.Constant.EVENT_SUCCESS
import club.biggerm.asionbo.wanandroid.utils.GlideBannerLoader
import club.biggerm.asionbo.wanandroid.utils.RxBus
import club.biggerm.asionbo.wanandroid.webview.ContentWebViewActivity
import cn.levey.bannerlib.RxBanner
import cn.levey.bannerlib.impl.RxBannerClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_wv_activity.*
import kotlinx.android.synthetic.main.main_activity.toolbar
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by asionbo on 2018/4/28.
 */

class MainActivity : BaseActivity() {

    var datas:List<Article> = listOf()
    var mCurrentCounter = 0
    var isErr = true
    var alreadyPage = 0
    val adapter: ArticleTitleAdapter = ArticleTitleAdapter(R.layout.article_list_item,datas)
    var rxBanner: RxBanner? = null


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        title = "玩安卓"
        getBanner()
        initView()
        getArticleList(alreadyPage)
        rxBanner = rxb_main.setLoader(GlideBannerLoader())
    }

    override fun onResume() {
        super.onResume()
        rxBanner!!.onResume()
        if (mDisposable == null || mDisposable!!.isDisposed){
            mDisposable = RxBus.INSTANCE.toObserverable(Event::class.java)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when (it.type){
                            EVENT_SUCCESS -> {
                                val post = it.post as List<Banner>
                                setBanner(post)
                            }
                            else ->{
                                Log.e("event","none")
                            }
                        }
                    },{
                        t -> showResultDialog(t.message!!)
                    })
        }
    }

    override fun onPause() {
        super.onPause()
        rxBanner!!.onPause()
    }

    private fun getBanner(){
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if(0 == result.errorCode){
                        RxBus.INSTANCE.post(Event(EVENT_SUCCESS,result.data))
                    }else{
                        showResultDialog(result.errorMsg)
                    }
                },{ error ->
                    error.printStackTrace()
                })
    }

    private fun getArticleList(page:Int) {
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if(0 == result.errorCode){
                        datas = result.data.datas
                        if (adapter.data.size <= 0){
                            adapter.setNewData(datas)
                        }else{
                            adapter.addData(datas)
                        }
                        isErr = true
                    }else{
                        isErr = false
                        showResultDialog(result.errorMsg)
                    }
                },{ error ->
                    isErr = false
                    error.printStackTrace()
                })
    }

    private fun initView() {
        rl_content.layoutManager = LinearLayoutManager(this)
        rl_content.adapter = adapter
        rl_content.isNestedScrollingEnabled = true

        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

        adapter.setOnItemClickListener { adapter1, view, position ->
            val item = adapter.getItem(position)
            goToWebView(item!!.link)
        }

        //下拉刷新
        adapter.isUpFetchEnable = true
        adapter.setUpFetchListener {
            var count = 0
            count = count++
            Log.e("fetch",count.toString())
            adapter.isUpFetching = true

            rl_content.postDelayed({
                adapter.addHeaderView(getView())
                adapter.isUpFetching = false
                if (count > 5){
                    adapter.isUpFetchEnable = false
                }

            },300)
        }
        adapter.setStartUpFetchPosition(1)

        //上拉加载
        adapter.setOnLoadMoreListener({
            rl_content.postDelayed({
                if (mCurrentCounter >= datas.size){
                    adapter.loadMoreEnd()
                }else{
                    if (isErr){
                        //成功获取更多数据
                        alreadyPage++
                        getArticleList(alreadyPage)
                        adapter.loadMoreComplete()
                    }else{
                        //获取更多数据失败
                        isErr = true
                        adapter.loadMoreFail()
                    }
                }
            },300)
        },rl_content)

    }


    fun setBanner(banners:List<Banner>){
        val imgs = mutableListOf<String>()
        val titls = mutableListOf<String>()
        for (banner in banners){
            imgs.add(banner.imagePath)
            titls.add(banner.title)
        }
//        val config = RxBannerConfig()
//        config.itemPercent = 70
//        config.setItemSpaceDp(0)
//        config.sideAlpha = 1.0f
//        config.itemScale = 0.5f
//        rxBanner!!.config = config
        rxBanner!!.setDatas(imgs,titls)
                .setOnBannerTitleClickListener { pos, title ->
                    goToWebView(banners[pos].url)
                }
                .setOnBannerClickListener(object :RxBannerClickListener{
                    override fun onItemLongClick(p0: Int, p1: Any?) {
                    }

                    override fun onItemClick(p0: Int, p1: Any?) {
                        goToWebView(banners[p0].url)
                    }
                })
                .start()
    }


    fun getView():TextView{
        val tv = TextView(this)
        tv.text = "header view"
        return tv
    }

    fun goToWebView(url:String){
        val intent = Intent(this, ContentWebViewActivity::class.java)
        intent.putExtra("article_url",url)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxBanner!!.onDestroy()
    }

    class clickListener: RxBannerClickListener{
        override fun onItemLongClick(p0: Int, p1: Any?) {
        }

        override fun onItemClick(p0: Int, p1: Any?) {

        }

    }
}