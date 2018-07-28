package club.biggerm.asionbo.wanandroid.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import club.biggerm.asionbo.wanandroid.BaseFragment
import club.biggerm.asionbo.wanandroid.R
import club.biggerm.asionbo.wanandroid.adapter.ArticleTitleAdapter
import club.biggerm.asionbo.wanandroid.model.Article
import club.biggerm.asionbo.wanandroid.model.Banner
import club.biggerm.asionbo.wanandroid.network.OpenApiService
import club.biggerm.asionbo.wanandroid.network.WebDataSource
import club.biggerm.asionbo.wanandroid.utils.GlideBannerLoader
import club.biggerm.asionbo.wanandroid.webview.ContentWebViewActivity
import cn.levey.bannerlib.RxBanner
import cn.levey.bannerlib.impl.RxBannerClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by asionbo on 2018/7/19.
 */

class HomeFragment : BaseFragment(){


    var rxBanner: RxBanner? = null
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    var mRecyclerView:RecyclerView? = null
    var datas:List<Article> = listOf()
    var mAdapter: ArticleTitleAdapter? = null
    var currentPage = 0

    override fun initView(view: View, savedInstanceState: Bundle?) {
        rxBanner = view.findViewById(R.id.rxb_main) as RxBanner
        rxBanner!!.setLoader(GlideBannerLoader())
        mSwipeRefreshLayout = view.findViewById(R.id.srl_refresh)
        mRecyclerView = view.findViewById(R.id.rl_content)
        mRecyclerView!!.layoutManager = LinearLayoutManager(mActivity)

        mAdapter = ArticleTitleAdapter(R.layout.article_list_item, datas)
        mRecyclerView!!.adapter = mAdapter
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        super.initData()
        mAdapter!!.setOnLoadMoreListener({
            Log.e("tag","loadmore")
            currentPage += 1
            getArticleList(currentPage)
        },mRecyclerView)

        mAdapter!!.setOnItemClickListener { baseQuickAdapter: BaseQuickAdapter<Any, BaseViewHolder>, view: View, i: Int ->
            goToWebView(datas[i].link,datas[i].title)
        }

        mAdapter!!.setOnItemChildClickListener { adapter, view, position ->
            run {
                showToast("click_child" + position)
            }
        }

        mSwipeRefreshLayout!!.setOnRefreshListener {
            Log.e("tag","refresh")
            val article = Article()
            article.title = "swipeRefresh"
            val textView = TextView(mActivity)
            textView.text = "swipeRefresh"
            mAdapter!!.addHeaderView(textView)
            mSwipeRefreshLayout!!.isRefreshing = false
        }
    }

    override fun setLazyLoad() {
        super.setLazyLoad()
        Log.e("tag","lazyLoad")
        getBanner()
        getArticleList(currentPage)
    }

    private fun getBanner(){
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if(0 == result.errorCode){
                        setBanner(result.data)
                    }else{

                    }
                },{ error ->
                    error.printStackTrace()
                })
    }

    fun setBanner(banners:List<Banner>){
        val imgs = mutableListOf<String>()
        val titls = mutableListOf<String>()
        for (banner in banners){
            imgs.add(banner.imagePath)
            titls.add(banner.title)
        }
        rxBanner!!.setDatas(imgs,titls)
                .setOnBannerTitleClickListener { pos, title ->
                    goToWebView(banners[pos].url,banners[pos].title)
                }
                .setOnBannerClickListener(object : RxBannerClickListener {
                    override fun onItemLongClick(p0: Int, p1: Any?) {
                        Log.e("tag","itemLongClick")
                    }

                    override fun onItemClick(p0: Int, p1: Any?) {
                        goToWebView(banners[p0].url,banners[p0].title)
                    }
                })
                .start()
    }

    fun goToWebView(url:String,title: String?){
        val intent = Intent(context, ContentWebViewActivity::class.java)
        intent.putExtra("article_url",url)
        intent.putExtra("title",title)
        startActivity(intent)
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
                        if (!mAdapter!!.data.isEmpty()){
                            mAdapter!!.addData(datas)
                        }else {
                            mAdapter!!.setNewData(datas)
                        }
                    }else{
//                        showResultDialog(result.errorMsg)
                    }
                },{ error ->
                    error.printStackTrace()
                })
    }

    override fun onResume() {
        super.onResume()
        Log.e("tag1","onresume")
        rxBanner!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.e("tag1","onpause")
        rxBanner!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("tag1","ondestory")
        rxBanner!!.onDestroy()
    }
}