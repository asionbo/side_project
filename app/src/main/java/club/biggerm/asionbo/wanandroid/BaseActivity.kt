package club.biggerm.asionbo.wanandroid

import android.support.v7.app.AppCompatActivity
import club.biggerm.asionbo.wanandroid.utils.AppUtils
import io.reactivex.disposables.Disposable

/**
 * Created by asionbo on 2018/4/28.
 */

open class BaseActivity : AppCompatActivity(){


    protected var mDisposable : Disposable? = null

    fun showResultDialog(result:String){
        AppUtils.showResultDialog(this,result)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDisposable != null && mDisposable!!.isDisposed){
            mDisposable!!.dispose()
        }
        mDisposable = null
    }
}