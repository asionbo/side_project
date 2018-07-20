package club.biggerm.asionbo.wanandroid

import android.app.Application
import android.os.Handler
import android.os.Process
import com.tencent.tac.TACApplication
import com.tencent.tac.analytics.TACAnalyticsOptions
import com.tencent.tac.analytics.TACAnalyticsService
import com.tencent.tac.analytics.TACAnalyticsStrategy
import com.tencent.tac.option.TACApplicationOptions

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

    override fun onCreate() {
        super.onCreate()
        instance = this
        mainID = Process.myTid()
        mainThreadHander = Handler()
        TACApplication.configure(this)

        val tacApplicationOptions:TACApplicationOptions = TACApplicationOptions.newDefaultOptions(this)
        val tacAnalyticsOptions: TACAnalyticsOptions = tacApplicationOptions.sub("analytics")

        tacAnalyticsOptions.strategy(TACAnalyticsStrategy.INSTANT)
        tacAnalyticsOptions.setWifiInstantSendEnabled(true)

        TACAnalyticsService.getInstance().start(this)
    }

}