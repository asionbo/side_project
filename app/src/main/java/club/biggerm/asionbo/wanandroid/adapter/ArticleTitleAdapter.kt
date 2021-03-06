package club.biggerm.asionbo.wanandroid.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import club.biggerm.asionbo.wanandroid.R.id.*
import club.biggerm.asionbo.wanandroid.model.Article
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso

/**
 * Created by asionbo on 2018/7/3.
 */

class ArticleTitleAdapter(layoutResId: Int, data: List<Article>) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(tv_article_title, item.title)
//        if (item.envelopePic.isEmpty()){
//            Picasso.get().load("https://upload-images.jianshu.io/upload_images/4317382-84abd0aa7b7d35bb.jpg")
//                    .fit().into(helper.getView<ImageView>(iv_article_pic))
//        }else {
//            Picasso.get().load(item.envelopePic).fit().into(helper.getView<ImageView>(iv_article_pic))
//        }
        val mTag = helper.getView<TextView>(tv_tag)
        if (item.tags.isNotEmpty()){
            mTag.text = item.tags[0].name
            mTag.visibility = View.VISIBLE
            helper.addOnClickListener(tv_tag)
        }else{
            mTag.visibility = View.GONE
        }
        helper.setText(tv_author,item.author).addOnClickListener(tv_author)
        helper.setText(tv_niceDate,item.niceDate)
    }
}