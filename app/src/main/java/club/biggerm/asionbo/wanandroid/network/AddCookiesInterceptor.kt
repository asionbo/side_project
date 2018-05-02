package club.biggerm.asionbo.wanandroid.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.prefs.Preferences

/**
 * Created by asionbo on 2018/5/2.
 */
class AddCookiesInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain?): Response? {
        try {
            val builder = chain!!.request().newBuilder()
            val cookie = Preferences.userRoot().get("cookies", "Cookie")
            builder.addHeader("Cookie",cookie)
            return chain.proceed(builder.build())
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}