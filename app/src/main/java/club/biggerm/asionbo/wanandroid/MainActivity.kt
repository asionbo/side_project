package club.biggerm.asionbo.wanandroid

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import club.biggerm.asionbo.wanandroid.adapter.ArticleTitleAdapter
import club.biggerm.asionbo.wanandroid.model.Article
import club.biggerm.asionbo.wanandroid.network.OpenApiService
import club.biggerm.asionbo.wanandroid.network.WebDataSource
import club.biggerm.asionbo.wanandroid.webview.ContentWebViewActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initView()
        getArticleList(alreadyPage)
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
            val intent = Intent(this, ContentWebViewActivity::class.java)
            Log.e("item",item.toString())
            intent.putExtra("article",item!!.link)
            startActivity(intent)
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


    fun getView():TextView{
        val tv = TextView(this)
        tv.text = "header view"
        return tv
    }
}