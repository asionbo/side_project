package club.biggerm.asionbo.wanandroid.model

/**
 * Created by asionbo on 2018/5/2.
 */
class ArticleTotal{
//    "curPage": 1,
//    "offset": 0,
//    "over": false,
//    "pageCount": 62,
//    "size": 20,
//    "total": 1238
//    "datas": []
    var curPage:Int = 0
    var offset:Int = 0
    var over:Boolean = false
    var pageCount:Long = 0
    var size :Long = 0
    var total:Long = 0
    var datas:List<Article> = listOf(Article())
}