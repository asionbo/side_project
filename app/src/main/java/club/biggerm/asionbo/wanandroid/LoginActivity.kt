package club.biggerm.asionbo.wanandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import club.biggerm.asionbo.wanandroid.network.OpenApiService
import club.biggerm.asionbo.wanandroid.network.WebDataSource
import club.biggerm.asionbo.wanandroid.utils.Constant
import club.biggerm.asionbo.wanandroid.utils.PreferenceUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*

/**
 * login
 */
class LoginActivity : BaseActivity() {

    var atmpHistory: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.action_sign_in)

        initData()
        initView()
    }

    private fun initView() {
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, atmpHistory)
        atv_account.setAdapter(arrayAdapter)

        btn_login.setOnClickListener { v: View? ->
            if (beforeLogin()) {
                login()
            }
        }
    }

    private fun initData() {
        val allHistory = PreferenceUtils.getParam(this, Constant.ATTEMPT_LOGIN_HISTORY, "") as String

        var hh = allHistory.split(",".toRegex())
        val list = ArrayList<String>(hh)
        atmpHistory = list
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun beforeLogin(): Boolean {
        if (TextUtils.isEmpty(atv_account.text)) {
            showResultDialog(getString(R.string.error_empty_account))
            return false
        } else if (TextUtils.isEmpty(et_password.text)) {
            showResultDialog(getString(R.string.error_empty_password))
            return false
        }
        return true
    }

    fun login() {
        WebDataSource.instance.retrofit.create(OpenApiService::class.java)
                .login(atv_account.text.toString(), et_password.text.toString())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if(result.errorCode < 0){
                        showResultDialog(result.errorMsg)
                    }else{
                        //success
                        afterLogin()
                    }
                }, { error ->
                    error.printStackTrace()
                })
    }

    private fun afterLogin() {
        if (atmpHistory!!.size > 0) {
            for (history in atmpHistory!!) {
                if (history.equals(atv_account.text.toString())) {
                    atmpHistory!!.remove(history)
                    break
                }
            }
            atmpHistory!!.add(0, atv_account.text.toString())
            if (atmpHistory!!.size > 10) {
                atmpHistory!!.removeAt(atmpHistory!!.size - 1)
            }

            val sb = StringBuilder()
            for (h in atmpHistory!!) {
                sb.append(h + ",")
            }

            PreferenceUtils.setParam(this, Constant.ATTEMPT_LOGIN_HISTORY, sb.toString())
        } else {
            PreferenceUtils.setParam(this,Constant.ATTEMPT_LOGIN_HISTORY,atv_account.text.toString()+",")
        }

        val intent = Intent(this,ProfileActivity::class.java)
        intent.putExtra(Constant.INTENT_ACCOUNT,atv_account.text.toString())
        startActivityForResult(intent, Activity.RESULT_OK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("requestCode",requestCode.toString())
        Log.e("resultCode",resultCode.toString())
    }

}
