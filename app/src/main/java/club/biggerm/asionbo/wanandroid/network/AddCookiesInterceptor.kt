package club.biggerm.asionbo.wanandroid.network

import club.biggerm.asionbo.wanandroid.MyApplication
import club.biggerm.asionbo.wanandroid.utils.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by asionbo on 2018/5/2.
 */
class AddCookiesInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain?): Response? {
        try {
            val builder = chain!!.request().newBuilder()
            val cookies : String = PreferenceUtils.getParam(MyApplication.instance(),"Cookies", "") as String

            builder.addHeader("Cookie",cookies)
            return chain.proceed(builder.build())
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}