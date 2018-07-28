package club.biggerm.asionbo.wanandroid.network

import club.biggerm.asionbo.wanandroid.model.ArticleTotal
import club.biggerm.asionbo.wanandroid.model.Banner
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


//    login response header
//    HTTP/1.1 200 OK
//    Server: Apache-Coyote/1.1
//    Set-Cookie: JSESSIONID=3BB395FC33C5B4EBD6BBE59874310374; Path=/; HttpOnly
//    Set-Cookie: loginUserName=wan_01; Expires=Thu, 23-Aug-2018 01:37:54 GMT; Path=/
//    Set-Cookie: loginUserPassword=123456; Expires=Thu, 23-Aug-2018 01:37:54 GMT; Path=/
//    Content-Type: application/json;charset=UTF-8
//    Transfer-Encoding: chunked
//    Date: Tue, 24 Jul 2018 01:37:53 GMT
    @POST("login")
    fun login(@Query("username") username: String,@Query("password") password: String):Observable<BaseResult<User>>

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page:Int):Observable<BaseResult<ArticleTotal>>

    @GET("banner/json")
    fun getBanner():Observable<BaseResult<List<Banner>>>
}