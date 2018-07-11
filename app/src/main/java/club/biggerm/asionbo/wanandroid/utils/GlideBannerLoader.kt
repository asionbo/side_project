package club.biggerm.asionbo.wanandroid.utils

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import cn.levey.bannerlib.impl.RxBannerLoaderInterface

/**
 * Created by asionbo on 2018/7/10.
 */
class GlideBannerLoader : RxBannerLoaderInterface<ImageView> {
    override fun show(context: Context, o: Any, imageView: ImageView) {
        Glide.with(context)
                .load(o)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView)
    }

    override fun create(context: Context): ImageView {
        return ImageView(context)
    }
}
