package club.biggerm.asionbo.wanandroid.network

import club.biggerm.asionbo.wanandroid.model.ArticleTotal
import club.biggerm.asionbo.wanandroid.model.BaseResult
import club.biggerm.asionbo.wanandroid.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by asionbo on 2018/4/11.
 */
interface OpenApiService{


    @POST("user/register")
    fun register(@Query("username") username: String,@Query("password") password: String
    ,@Query("repassword") repassword:String):Observable<BaseResult<User>>


    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page:Int):Observable<BaseResult<ArticleTotal>>
}