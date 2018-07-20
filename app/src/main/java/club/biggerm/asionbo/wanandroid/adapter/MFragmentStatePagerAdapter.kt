package club.biggerm.asionbo.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import java.util.ArrayList

/**
 * Created by Administrator on 2016/10/13 0013.
 */
class MFragmentStatePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragments = ArrayList<Fragment>()
    private val mFragmentsTitles = ArrayList<String>()

    fun clear() {
        mFragments.clear()
        mFragmentsTitles.clear()
        notifyDataSetChanged()
    }

    fun addFragment(fragment: Fragment, fragmentTitle: String) {
        mFragments.add(fragment)
        mFragmentsTitles.add(fragmentTitle)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentsTitles[position]
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}
