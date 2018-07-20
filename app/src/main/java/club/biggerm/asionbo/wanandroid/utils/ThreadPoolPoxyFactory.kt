package club.biggerm.asionbo.wanandroid.utils

/**
 * Created by asionbo on 2018/7/17.
 */
object ThreadPoolPoxyFactory {
    var mNormalThreadPoolPoxy: ThreadPoolPoxy? = null
    var mDownloadThreadPoolPoxy: ThreadPoolPoxy? = null


    /**
     * 生成常规线程池
     */
    fun getNormalThreadPoolPoxy(): ThreadPoolPoxy {
        if (mNormalThreadPoolPoxy == null) {
            synchronized(ThreadPoolPoxyFactory::class) {
                if (mNormalThreadPoolPoxy == null) {
                    mNormalThreadPoolPoxy = ThreadPoolPoxy(5, 5, 3000)
                }

            }
        }
        return mNormalThreadPoolPoxy!!
    }


    /**
     * 生成常规线程池
     */
    fun getDowloadThreadPoolPoxy(): ThreadPoolPoxy {
        if (mDownloadThreadPoolPoxy == null) {
            synchronized(ThreadPoolPoxyFactory::class) {
                if (mDownloadThreadPoolPoxy == null) {
                    mDownloadThreadPoolPoxy = ThreadPoolPoxy(5, 5, 3000)
                }

            }
        }
        return mDownloadThreadPoolPoxy!!
    }


}