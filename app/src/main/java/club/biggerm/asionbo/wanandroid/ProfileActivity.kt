package club.biggerm.asionbo.wanandroid

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import club.biggerm.asionbo.wanandroid.BaseFragment
import club.biggerm.asionbo.wanandroid.LoginActivity
import club.biggerm.asionbo.wanandroid.R
import club.biggerm.asionbo.wanandroid.utils.Constant
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
        val account = intent.getStringExtra(Constant.INTENT_ACCOUNT)
        title = account
        Glide.with(this)
                .load("https://upload-images.jianshu.io/upload_images/4317382-73ce3129bb6f242e.jpg")
                .apply(RequestOptions.centerCropTransform())
                .into(iv_scrolling_top)

        collapsing_toolbar_layout.setCollapsedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT))
    }


}