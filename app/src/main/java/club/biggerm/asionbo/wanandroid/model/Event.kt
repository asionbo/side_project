package club.biggerm.asionbo.wanandroid.model

/**
 * Created by asionbo on 2018/7/9.
 */
class Event {
    var type: Int = 0
    var post: Any? = null

    constructor(type: Int) {
        this.type = type
    }

    constructor(type: Int, post: Any?) {
        this.type = type
        this.post = post
    }
}
