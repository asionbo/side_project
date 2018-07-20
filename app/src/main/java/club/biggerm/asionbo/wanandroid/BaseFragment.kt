package club.biggerm.asionbo.wanandroid

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import club.biggerm.asionbo.wanandroid.fragment.LoadPage

/**
 * Created by asionbo on 2018/7/16.
 */
abstract class BaseFragment : Fragment(){

    var loadPage:LoadPage? = null
    var mActivity: Activity? = null
    val FRAGMENT_IS_HIDDEN:String = "fragment_is_hidden"
    protected var isSee:Boolean = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mActivity = activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null){
            val isHidden = savedInstanceState.getBoolean(FRAGMENT_IS_HIDDEN)
            val transaction = fragmentManager!!.beginTransaction()
            when(isHidden){
                true ->{
                    transaction.hide(this)
                }
                false ->{
                    transaction.show(this)
                }
            }
            transaction.commit()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(mActivity).inflate(setLayoutId(),container,false)
        initView(view,savedInstanceState)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        when(isVisibleToUser){
            true ->{
                isSee = true
                onVisible()
            }
            false ->{
                isSee = false
                onInVisible()
            }
        }
    }

    open fun onVisible(){
        setLazyLoad()
    }

    open fun onInVisible(){

    }

    /**
     * 懒加载数据
     */
    open fun setLazyLoad(){

    }


    /**
     * 初始化数据
     */
    open fun initData(){

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FRAGMENT_IS_HIDDEN,isHidden)
    }

    internal abstract fun setLayoutId():Int
    internal abstract fun initView(view:View,savedInstanceState: Bundle?)

}