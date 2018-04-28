package club.biggerm.asionbo.wanandroid

import android.app.Application
import com.tencent.tac.TACApplication
import com.tencent.tac.analytics.TACAnalyticsOptions
import com.tencent.tac.analytics.TACAnalyticsService
import com.tencent.tac.analytics.TACAnalyticsStrategy
import com.tencent.tac.option.TACApplicationOptions

/**
 * Created by asionbo on 2018/4/19.
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        TACApplication.configure(this)

        val tacApplicationOptions:TACApplicationOptions = TACApplicationOptions.newDefaultOptions(this)
        val tacAnalyticsOptions: TACAnalyticsOptions = tacApplicationOptions.sub("analytics")

        tacAnalyticsOptions.strategy(TACAnalyticsStrategy.INSTANT)
        tacAnalyticsOptions.setWifiInstantSendEnabled(true)

        TACAnalyticsService.getInstance().start(this)
    }
}