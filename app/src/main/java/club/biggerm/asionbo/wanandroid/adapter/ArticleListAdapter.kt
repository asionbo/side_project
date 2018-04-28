package club.biggerm.asionbo.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import club.biggerm.asionbo.wanandroid.R
import club.biggerm.asionbo.wanandroid.model.Article

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class DataViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

    }

}