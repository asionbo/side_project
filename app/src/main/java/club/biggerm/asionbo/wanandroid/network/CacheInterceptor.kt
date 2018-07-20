package club.biggerm.asionbo.wanandroid.network

import club.biggerm.asionbo.wanandroid.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.CacheControl



/**
 * Created by asionbo on 2018/7/12.
 */
class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        if (!AppUtils.isNetworkConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }

        val originalResponse = chain.proceed(request)
        if (AppUtils.isNetworkConnected()) {
            // 有网络时 设置缓存为默认值
            val cacheControl = request.cacheControl().toString()
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
        } else {
            // 无网络时 设置超时为1周
            val maxStale = 60 * 60 * 24 * 7
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
        }
    }

}