package club.biggerm.asionbo.wanandroid

import android.annotation.SuppressLint
import android.os.Bundle
import club.biggerm.asionbo.wanandroid.adapter.MFragmentStatePagerAdapter
import club.biggerm.asionbo.wanandroid.fragment.HomeFragment
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by asionbo on 2018/4/28.
 */

class MainActivity : BaseActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
//        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        title = "玩安卓"
        initFragment()


    }

    private fun initFragment() {

        val pagerAdapter = MFragmentStatePagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        pagerAdapter.addFragment(HomeFragment(),"首页")
        pagerAdapter.addFragment(HomeFragment(),"导航")
        pagerAdapter.notifyDataSetChanged()

    }

}