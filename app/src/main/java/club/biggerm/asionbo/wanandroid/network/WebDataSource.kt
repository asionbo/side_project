package club.biggerm.asionbo.wanandroid.network

import club.biggerm.asionbo.wanandroid.utils.BASE_URL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by asionbo on 2018/4/11.
 */

class WebDataSource private constructor(){


    private fun getLogInterceptor() : HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    var retrofit:Retrofit = initRetrofit()

    private object Holder{
        val INSTANCE = WebDataSource()
    }

    companion object {
        val instance:WebDataSource by lazy { Holder.INSTANCE }
    }


    private fun initRetrofit():Retrofit{
        val builder = OkHttpClient.Builder()
                .addInterceptor(getLogInterceptor())
                .addInterceptor(ReceivedCookiesInterceptor())
                .addInterceptor(AddCookiesInterceptor())
                .readTimeout(100,TimeUnit.SECONDS)
                .connectTimeout(300,TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL+"/")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build()).build()

        return retrofit
    }

}
