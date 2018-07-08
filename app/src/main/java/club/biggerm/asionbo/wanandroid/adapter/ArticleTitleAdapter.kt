package club.biggerm.asionbo.wanandroid.adapter

import android.widget.ImageView
import club.biggerm.asionbo.wanandroid.R.id.iv_article_pic
import club.biggerm.asionbo.wanandroid.R.id.tv_article_title
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
        if (item.envelopePic.isEmpty()){
            Picasso.get().load("https://upload-images.jianshu.io/upload_images/4317382-84abd0aa7b7d35bb.jpg")
                    .fit().into(helper.getView<ImageView>(iv_article_pic))
        }else {
            Picasso.get().load(item.envelopePic).fit().into(helper.getView<ImageView>(iv_article_pic))
        }
    }
}