package club.biggerm.asionbo.wanandroid.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import club.biggerm.asionbo.wanandroid.BaseActivity
import club.biggerm.asionbo.wanandroid.R
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import kotlinx.android.synthetic.main.content_wv_activity.*

/**
 * Created by asionbo on 2018/7/8.
 */
class ContentWebViewActivity:BaseActivity(){

    var mAgentWeb:AgentWeb? = null
    var mArticleLink: String? = ""
    var mTitle: String? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_wv_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        mArticleLink = intent.getStringExtra("article_url")
        mTitle = intent.getStringExtra("title")
        if (mTitle != null){
            title = mTitle
        }else{
            title = mArticleLink
        }
        initView()
    }

    private fun initView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(ll_content, FrameLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(mArticleLink)
    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb!!.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (!mAgentWeb!!.back()){
            super.onBackPressed()
        }
    }

}