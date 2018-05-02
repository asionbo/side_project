package club.biggerm.asionbo.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import club.biggerm.asionbo.wanandroid.R
import club.biggerm.asionbo.wanandroid.model.Article
import com.squareup.picasso.Picasso

/**
 * Created by asionbo on 2018/4/28.
 */
class ArticleListAdapter(private val context:Context): RecyclerView.Adapter<ArticleListAdapter.DataViewHolder>() {

    var data : List<Article> = listOf()

    fun addData(list : List<Article>){
        this.data = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.article_list_item,parent,false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class DataViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val article = data.get(position)
            val pic = itemView.findViewById<ImageView>(R.id.iv_article_pic)
            val title = itemView.findViewById<TextView>(R.id.tv_article_title)
            if (!TextUtils.isEmpty(article.envelopePic)){
                Picasso.get().load(article.envelopePic).fit().into(pic)
            }else{
                Picasso.get().load(R.mipmap.ic_launcher_round).into(pic)
            }
            title.setText(article.title)
        }
    }

}