package club.biggerm.asionbo.wanandroid.utils

import java.util.concurrent.*

/**
 * Created by asionbo on 2018/7/17.
 */

class ThreadPoolPoxy(var corePoolSize:Int,var maxPoolSize:Int,var keepAliveTime:Long){

    var mExecutor: ThreadPoolExecutor? = null

    fun submit(task:Runnable){
        initThreadPoolExecutor()
        mExecutor!!.submit(task)
    }

    fun execute(task:Runnable){
        initThreadPoolExecutor()
        mExecutor!!.execute(task)
    }

    fun remove(task: Runnable){
        initThreadPoolExecutor()
        mExecutor!!.remove(task)
    }


    /**
     * 初始化线程池
     */
    fun initThreadPoolExecutor(){
        val timeUnit = TimeUnit.MILLISECONDS
        val workQueue : BlockingDeque<Runnable> = LinkedBlockingDeque<Runnable>()
        val threadFactory = Executors.defaultThreadFactory()
        val handler : RejectedExecutionHandler = ThreadPoolExecutor.DiscardPolicy()

        if (mExecutor == null || mExecutor!!.isShutdown || mExecutor!!.isTerminated){
            mExecutor = ThreadPoolExecutor(
                    corePoolSize,//线程池核心数
                    maxPoolSize,//最大线程数
                    keepAliveTime,//保持时间
                    timeUnit,//时间单位
                    workQueue,//工作队列
                    threadFactory,//线程工厂
                    handler)
        }
    }

}