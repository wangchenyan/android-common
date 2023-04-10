package me.wcy.common.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import me.wcy.common.CommonApp
import me.wcy.common.model.CommonResult

/**
 * Created by wangchenyan.top on 2022/10/18.
 */
object ImageUtils {

    fun loadBitmap(url: Any, callback: (CommonResult<Bitmap>) -> Unit) {
        Glide.with(CommonApp.app)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(CommonResult.success(resource))
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback(CommonResult.fail())
                }
            })
    }
}