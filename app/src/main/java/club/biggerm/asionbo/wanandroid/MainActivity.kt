package club.biggerm.asionbo.wanandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import club.biggerm.asionbo.wanandroid.adapter.ArticleListAdapter
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

    var datas:List<Article>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        getArticleList()

        var layoutManager : LinearLayoutManager
    }

    private fun getArticleList() {
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .getArticleList(0)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if(0 == result.errorCode){
                        datas = result.data
                        refreshContentView()
                    }else{
                        AppUtils.showResultDialog(this,result.errorMsg)
                    }
                },{ error ->
                    error.printStackTrace()
                })
    }

    private fun refreshContentView() {
        ArticleListAdapter(this)

        rl_content.layoutManager = LinearLayoutManager(this)
        rl_content.adapter
    }

}