package club.biggerm.asionbo.wanandroid.utils

import android.content.Context
import android.telephony.TelephonyManager
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
}
