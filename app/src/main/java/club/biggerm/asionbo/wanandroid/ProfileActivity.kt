package club.biggerm.asionbo.wanandroid

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import club.biggerm.asionbo.wanandroid.utils.Constant
import club.biggerm.asionbo.wanandroid.utils.StatusbarUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.profile_activity.*

/**
 * Created by asionbo on 2018/8/15.
 */

class ProfileActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initView()

    }
    private fun initView(){
        StatusbarUtils.setTranslucent(this)
        val account = intent.getStringExtra(Constant.INTENT_ACCOUNT)
        Glide.with(this)
                .load("https://upload-images.jianshu.io/upload_images/4317382-73ce3129bb6f242e.jpg")
                .apply(RequestOptions.centerCropTransform())
                .into(iv_scrolling_top)

        collapsing_toolbar_layout.setContentScrimColor(resources.getColor(R.color.colorPrimary))
        collapsing_toolbar_layout.title = account
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home ->{
                onBackPressed()
            }
            R.id.more ->{
                showResultDialog("this is more")
            }
        }
        return super.onOptionsItemSelected(item)
    }


}