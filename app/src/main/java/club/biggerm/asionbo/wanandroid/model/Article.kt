package club.biggerm.asionbo.wanandroid.model

/**
 * Created by asionbo on 2018/4/28.
 */

class Article{

//    "apkLink": "",
//    "author": "l455202325",
//    "chapterId": 337,
//    "chapterName": "快应用",
//    "collect": false,
//    "courseId": 13,
//    "desc": "快应用API Demo 集合 QuickAPP\r\n",
//    "envelopePic": "http://www.wanandroid.com/blogimgs/589f3b01-d9d5-41b0-aeff-604b900aacd1.png",
//    "fresh": true,
//    "id": 2879,
//    "link": "http://www.wanandroid.com/blog/show/2120",
//    "niceDate": "17小时前",
//    "origin": "",
//    "projectLink": "https://github.com/l455202325/APIDemo",
//    "publishTime": 1524832705000,
//    "superChapterId": 294,
//    "superChapterName": "开源项目主Tab",
//    "tags": [
//    {
//        "name": "项目",
//        "url": "/project/list/1?cid=337"
//    }
//    ],
//    "title": "快应用API Demo 集合 QuickAPP APIDemo",
//    "type": 0,
//    "visible": 1,
//    "zan": 0

    var apkLink:String = ""
    var author: String = ""
    var chapterId: Long = 0
    var chapterName: String =""
    var collect: Boolean = false
    var courseId: Long = 0
    var desc: String = ""
    var envelopePic:String =""
    var fresh: Boolean = false
    var id: Long = 0
    var link: String = ""
    var niceDate: String = ""
    var origin: String = ""
    var projectLink: String = ""
    var publishTime: Long = 0
    var superChapterId: Long = 0
    var superChapterName: String = ""
    var tags: List<Tags> = listOf(Tags())
    var title: String = ""
    var type: Int = 0
    var visible: Int = 0
    var zan: Int = 0
    override fun toString(): String {
        return "Article(apkLink='$apkLink', author='$author', chapterId=$chapterId, chapterName='$chapterName', collect=$collect, courseId=$courseId, desc='$desc', envelopePic='$envelopePic', fresh=$fresh, id=$id, link='$link', niceDate='$niceDate', origin='$origin', projectLink='$projectLink', publishTime=$publishTime, superChapterId=$superChapterId, superChapterName='$superChapterName', tags=$tags, title='$title', type=$type, visible=$visible, zan=$zan)"
    }


}