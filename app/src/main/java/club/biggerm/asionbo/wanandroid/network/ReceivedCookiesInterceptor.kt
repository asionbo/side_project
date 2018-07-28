package club.biggerm.asionbo.wanandroid.network

import club.biggerm.asionbo.wanandroid.MyApplication
import club.biggerm.asionbo.wanandroid.utils.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by asionbo on 2018/5/2.
 */
class ReceivedCookiesInterceptor:Interceptor{


    override fun intercept(chain: Interceptor.Chain?): Response? {
        try {
            val originalResponse = chain!!.proceed(chain.request())
            if (!originalResponse.headers("Set-Cookie").isEmpty()){
                val cookies = StringBuilder()
                for (header in originalResponse.headers("Set-Cookie")){
                    cookies.append(header)
                }
                PreferenceUtils.setParam(MyApplication.instance(),"Cookies",cookies.toString())
            }
            return originalResponse
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }

}