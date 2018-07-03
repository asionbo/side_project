package club.biggerm.asionbo.wanandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import club.biggerm.asionbo.wanandroid.adapter.ArticleTitleAdapter
import club.biggerm.asionbo.wanandroid.model.Article
import club.biggerm.asionbo.wanandroid.network.OpenApiService
import club.biggerm.asionbo.wanandroid.network.WebDataSource
import club.biggerm.asionbo.wanandroid.utils.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by asionbo on 2018/4/28.
 */

class MainActivity : AppCompatActivity() {

    var datas:List<Article> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        getArticleList()
    }

    private fun getArticleList() {
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .getArticleList(0)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if(0 == result.errorCode){
                        datas = result.data.datas
                        Log.e("datas",datas.toString())
                        refreshContentView()
                    }else{
                        AppUtils.showResultDialog(this,result.errorMsg)
                    }
                },{ error ->
                    error.printStackTrace()
                })
    }

    private fun refreshContentView() {
//        val mAdapter = ArticleListAdapter(this)
        val adapter= ArticleTitleAdapter(R.layout.article_list_item,datas)
        rl_content.layoutManager = LinearLayoutManager(this)
        rl_content.adapter = adapter
        rl_content.isNestedScrollingEnabled = true
//        mAdapter.addData(datas)

        adapter.setOnItemClickListener { adapter1, view, position ->
            Toast.makeText(this,"click"+position,Toast.LENGTH_SHORT).show()
        }
    }
}