package club.biggerm.asionbo.wanandroid.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import club.biggerm.asionbo.wanandroid.BaseFragment
import club.biggerm.asionbo.wanandroid.R
import club.biggerm.asionbo.wanandroid.model.Article
import club.biggerm.asionbo.wanandroid.model.Banner
import club.biggerm.asionbo.wanandroid.network.OpenApiService
import club.biggerm.asionbo.wanandroid.network.WebDataSource
import club.biggerm.asionbo.wanandroid.webview.ContentWebViewActivity
import cn.levey.bannerlib.RxBanner
import cn.levey.bannerlib.impl.RxBannerClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by asionbo on 2018/7/16.
 */

class MainFragment: BaseFragment(){
    override fun initView(view: View, savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_main
    }

    var datas:List<Article> = listOf()

    private lateinit var rxBanner: RxBanner




    private fun getArticleList(page:Int) {
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if(0 == result.errorCode){
                        datas = result.data.datas
                    }else{
//                        showResultDialog(result.errorMsg)
                    }
                },{ error ->
                    error.printStackTrace()
                })
    }

    fun goToWebView(url:String){
        val intent = Intent(context, ContentWebViewActivity::class.java)
        intent.putExtra("article_url",url)
        startActivity(intent)
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
        rxBanner.setDatas(imgs,titls)
                .setOnBannerTitleClickListener { pos, title ->
                    goToWebView(banners[pos].url)
                }
                .setOnBannerClickListener(object : RxBannerClickListener {
                    override fun onItemLongClick(p0: Int, p1: Any?) {
                    }

                    override fun onItemClick(p0: Int, p1: Any?) {
                        goToWebView(banners[p0].url)
                    }
                })
                .start()
    }

    override fun onResume() {
        super.onResume()
        Log.e("tag1","onresume")
        rxBanner.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.e("tag1","onpause")
        rxBanner.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("tag1","ondestory")
        rxBanner.onDestroy()
    }


}