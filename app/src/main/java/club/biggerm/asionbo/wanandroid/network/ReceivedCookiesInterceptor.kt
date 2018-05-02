package club.biggerm.asionbo.wanandroid.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.prefs.Preferences

/**
 * Created by asionbo on 2018/5/2.
 */
class ReceivedCookiesInterceptor:Interceptor{


    override fun intercept(chain: Interceptor.Chain?): Response? {
        try {
            val originalResponse = chain!!.proceed(chain.request())
            if (!originalResponse.headers("Set-Cookie").isEmpty()){
                var cookies = ""
                for (header in originalResponse.headers("Set-Cookie")){
                    if (header.contains("JSESSIONID"))
                        cookies = header
                }
                Preferences.userRoot().put("cookies",cookies);
            }
            return originalResponse
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }

}