package club.biggerm.asionbo.wanandroid.fragment

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import club.biggerm.asionbo.wanandroid.MyApplication
import club.biggerm.asionbo.wanandroid.R
import club.biggerm.asionbo.wanandroid.utils.AppUtils
import club.biggerm.asionbo.wanandroid.utils.ThreadPoolPoxyFactory
import kotlinx.android.synthetic.main.page_error.view.*


/**
 * Created by asionbo on 2018/7/16.
 */
abstract class LoadPage(context: Context) : FrameLayout (context){
    private var mErrorView: View? = null//失败页
    private var mEmptyView: View? = null//空白页
    private var mLoadingView: View? = null//加载页
    private var mSuccessView: View? = null//成功页

    private val STATE_LOADING = 0
    val STATE_ERROR = 1
    val STATE_EMPTY = 2
    val STATE_SUCCESS = 3
    private var mCurState = STATE_LOADING
    private var mLoadDataTask: LoadDataTask? = null


    init {
        //初始化fragment公共界面(加载中,失败,空白)
        mErrorView = View.inflate(MyApplication.instance(), R.layout.page_error, null)
        this.addView(mErrorView)

        mEmptyView = View.inflate(MyApplication.instance(), R.layout.page_empty, null)
        this.addView(mEmptyView)

        mLoadingView = View.inflate(MyApplication.instance(), R.layout.page_loading, null)
        this.addView(mLoadingView)
        refreshViewByState()
    }


    /**
     * 根据当前状态显示页面
     */
    private fun refreshViewByState() {
        mErrorView!!.setVisibility(if (mCurState == STATE_ERROR) View.VISIBLE else View.GONE)
        mEmptyView!!.setVisibility(if (mCurState == STATE_EMPTY) View.VISIBLE else View.GONE)
        mLoadingView!!.setVisibility(if (mCurState == STATE_LOADING) View.VISIBLE else View.GONE)

        mErrorView!!.tv_error.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                //点击重试后,重新刷新数据
                triggerLoadData()
            }
        })

        if (mCurState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView()
            this.addView(mSuccessView)
        }

        if (mSuccessView != null) {
            mSuccessView!!.setVisibility(if (mCurState == STATE_SUCCESS) View.VISIBLE else View.GONE)
        }
    }

    /**
     * 外界希望通过loadingPager来触发加载数据的方法
     */
    fun triggerLoadData() {
        //在点击每个界面后,首先显示加载界面,因此状态码为LOADING
        //如果数据加载成功,就不用再显示加载页面
        //同时只能进行一次任务,如果在加载数据中,那么就不用重复加载,因此将mLoadDataTask设定为开关
        if (mCurState != STATE_SUCCESS && mLoadDataTask == null) {
            mCurState = STATE_LOADING
            refreshViewByState()

            //在子线程执行加载数据
            mLoadDataTask = LoadDataTask()
            ThreadPoolPoxyFactory.getNormalThreadPoolPoxy().execute(mLoadDataTask!!)
        }
    }

    inner class LoadDataTask : Runnable {
        override fun run() {
            //模拟网络请求延迟
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            mCurState = initData().state
            //加载数据后,需要根据数据的获取情况来显示页面
            AppUtils.safetyPostTask(Runnable {
                //获取数据状态后,显示对应的界面
                refreshViewByState()
                //完成一次任务后,置空mLoadDataTask
                mLoadDataTask = null
            })

        }
    }

    //定义枚举状态,来限定数据返回的状态
    enum class LoadResultState (state: Int) {
        SUCCESS(3), ERROR(1), EMPTY(2);

        var state: Int = 0
            internal set

        init {
            this.state = state
        }

    }

    abstract fun initData(): LoadResultState

    abstract fun initSuccessView(): View

}