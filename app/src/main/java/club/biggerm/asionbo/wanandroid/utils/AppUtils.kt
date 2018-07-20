package club.biggerm.asionbo.wanandroid.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Process
import android.support.annotation.MainThread
import android.telephony.TelephonyManager
import club.biggerm.asionbo.wanandroid.MyApplication
import club.biggerm.asionbo.wanandroid.R
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created by asionbo on 2018/4/28.
 */
object AppUtils{


    /**
     * show result dialog,just for Tips
     */
    fun showResultDialog(context: Context, content:String){
        MaterialDialog.Builder(context)
                .icon(context.resources.getDrawable(R.drawable.ic_error_outline_blue_400_24dp))
                .title(R.string.dialog_tips)
                .content(content)
                .positiveText(R.string.dialog_i_know)
                .onPositive{dialog,which->
                    dialog.dismiss()
                }.show()
    }

    /**
     * 获取设备IMEI
     */
    fun getIMEI(context: Context){
        val manager : TelephonyManager
        manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    }

    /**
     * 当前网络是否可用
     */
    fun isNetworkConnected():Boolean{
        val manager = MyApplication.instance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager.activeNetworkInfo != null){
            return manager.activeNetworkInfo.isAvailable
        }
        return false
    }

    /**
     * 安全执行任务方法
     */
    fun safetyPostTask(task:Runnable){
        val currentTid = Process.myTid()
        val mainId = MyApplication.mainId()
        if (currentTid == mainId){
            task.run()
        }else{
            val mainHander = MyApplication.mainHander()
            mainHander.post(task)
        }
    }
}
