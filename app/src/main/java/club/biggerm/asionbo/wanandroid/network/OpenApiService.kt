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
//    08-15 14:56:46.961 7714-7840/club.biggerm.asionbo.wanandroid D/OkHttp: <-- 200 OK http://www.wanandroid.com/user/login?username=wan_01&password=123456 (176ms)
//    Server: Apache-Coyote/1.1
//    Set-Cookie: loginUserName=wan_01; Expires=Fri, 14-Sep-2018 06:56:50 GMT; Path=/
//    Set-Cookie: loginUserPassword=123456; Expires=Fri, 14-Sep-2018 06:56:50 GMT; Path=/
//    08-15 14:56:46.964 7714-7840/club.biggerm.asionbo.wanandroid D/OkHttp: Set-Cookie: token_pass=5d9b90bcb70640183e09d1e755ead823; Expires=Fri, 14-Sep-2018 06:56:50 GMT; Path=/
//    08-15 14:56:46.965 7714-7840/club.biggerm.asionbo.wanandroid D/OkHttp: Content-Type: application/json;charset=UTF-8
//    Transfer-Encoding: chunked
//    Date: Wed, 15 Aug 2018 06:56:49 GMT
//    08-15 14:56:46.967 7714-7840/club.biggerm.asionbo.wanandroid D/OkHttp: {"data":{"collectIds":[],"email":"","icon":"","id":5215,"password":"123456","token":"","type":0,"username":"wan_01"},"errorCode":0,"errorMsg":""}
//    <-- END HTTP (145-byte body)
    @POST("user/login")
    fun login(@Query("username") username: String,@Query("password") password: String):Observable<BaseResult<User>>

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page:Int):Observable<BaseResult<ArticleTotal>>

    @GET("banner/json")
    fun getBanner():Observable<BaseResult<List<Banner>>>
}