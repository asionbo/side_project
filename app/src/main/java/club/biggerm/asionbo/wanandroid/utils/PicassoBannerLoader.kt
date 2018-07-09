package club.biggerm.asionbo.wanandroid.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView

import com.squareup.picasso.Picasso

import java.io.File

import cn.levey.bannerlib.impl.RxBannerLoaderInterface

/**
 * Created by asionbo on 2018/7/9.
 */
class PicassoBannerLoader : RxBannerLoaderInterface<ImageView> {

    override fun show(context: Context, path: Any, item: ImageView) {
        when(path){
            is Int ->{
                Picasso.get().load(path).into(item)
                return
            }
            is String ->{
                Picasso.get().load(path).into(item)
                return
            }
            is Uri ->{
                Picasso.get().load(path).into(item)
                return
            }
            is File ->{
                Picasso.get().load(path).into(item)
            }
        }
    }

    override fun create(context: Context): ImageView {
        return ImageView(context)
    }
}
