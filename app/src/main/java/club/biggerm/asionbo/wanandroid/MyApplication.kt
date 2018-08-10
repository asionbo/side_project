package club.biggerm.asionbo.wanandroid

import android.app.Application
import android.os.Handler
import android.os.Process

/**
 * Created by asionbo on 2018/4/19.
 */
class MyApplication: Application() {



    companion object {
        private var instance: Application? = null
        fun instance() = instance!!

        var mainID:Int? = null
        var mainThreadHander:Handler? = null
        fun mainId() = mainID!!
        fun mainHander() = mainThreadHander!!
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        try {
//            // 实例化一个新的配置
//            val tacApplicationOptions:TACApplicationOptions = TACApplicationOptions.newDefaultOptions(this)
//            val tacAnalyticsOptions: TACAnalyticsOptions = tacApplicationOptions.sub("analytics")
//
//            tacAnalyticsOptions.strategy(TACAnalyticsStrategy.INSTANT)
//
//            // 让自定义设置生效
//            TACApplication.configureWithOptions(this, tacApplicationOptions)
//        }catch (e:Exception){
//            e.printStackTrace()
//        }
//
//    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mainID = Process.myTid()
        mainThreadHander = Handler()
    }

}