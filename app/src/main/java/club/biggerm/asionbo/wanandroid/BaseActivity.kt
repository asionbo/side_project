package club.biggerm.asionbo.wanandroid

import android.content.Context
import android.support.v7.app.AppCompatActivity
import club.biggerm.asionbo.wanandroid.utils.AppUtils
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created by asionbo on 2018/4/28.
 */

open class BaseActivity : AppCompatActivity(){


    fun showResultDialog(result:String){
        AppUtils.showResultDialog(this,result)
    }
}