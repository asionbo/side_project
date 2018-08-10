package club.biggerm.asionbo.wanandroid.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    var mTitle: String? = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_wv_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web_view,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.share ->{
                val shareIntent = Intent()
                shareIntent.setAction(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,mTitle)
                shareIntent.putExtra(Intent.EXTRA_TEXT,mArticleLink)
                startActivity(Intent.createChooser(shareIntent,getString(R.string.dialog_share_title)))
            }
            R.id.browser ->{
                val browerIntent = Intent()
                browerIntent.setAction(Intent.ACTION_VIEW)
                browerIntent.setData(Uri.parse(mArticleLink))
                val componentName = browerIntent.resolveActivity(packageManager)
                if (componentName != null){
                    startActivity(Intent.createChooser(browerIntent,getString(R.string.dialog_choose_browser_title)))
                }else{
                    showResultDialog(getString(R.string.there_is_no_fit_app))
                }
            }
        }
        return super.onOptionsItemSelected(item)
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