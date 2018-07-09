package club.biggerm.asionbo.wanandroid.utils

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

/**
 * Created by asionbo on 2018/7/9.
 */
enum class RxBus(){
    INSTANCE;

    private var mBus: Relay<Any>? = null

    init {
        mBus = PublishRelay.create<Any>().toSerialized()
    }


    fun post(event: Any) {
        mBus!!.accept(event)
    }

    fun <T> toObserverable(eventType: Class<T>): Observable<T> {
        return mBus!!.ofType(eventType)
    }

    fun toObservable(): Observable<Any>? {
        return mBus
    }

    fun hasObservers(): Boolean {
        return mBus!!.hasObservers()
    }
}