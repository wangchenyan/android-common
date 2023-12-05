package top.wangchenyan.android.common.ext

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import top.wangchenyan.android.common.CommonApp

/**
 * Created by wangchenyan on 2018/8/23.
 */

fun ImageView.load(url: Any?) {
    load(url, avatar = false, round = false, corners = 0)
}

fun ImageView.load(url: Any?, round: Boolean) {
    load(url, avatar = false, round = round, corners = 0)
}

fun ImageView.load(url: Any?, corners: Int) {
    load(url, avatar = false, round = false, corners = corners)
}

fun ImageView.loadAvatar(url: Any?, round: Boolean = true) {
    load(url, avatar = true, round = round, corners = 0)
}

fun ImageView.loadAvatar(url: Any?, corners: Int) {
    load(url, avatar = true, round = false, corners = corners)
}

fun ImageView.loadGif(url: Any?) {
    if (ActivityUtils.isActivityAlive(this.context).not()) {
        return
    }

    Glide.with(this)
        .asGif()
        .load(url)
        .into(this)
}

@SuppressLint("CheckResult")
private fun ImageView.load(
    url: Any?,
    avatar: Boolean,
    round: Boolean,
    corners: Int,
) {
    val imageLoaderConfig = CommonApp.config.imageLoaderConfig
    val placeholder = if (avatar) {
        imageLoaderConfig.placeholderAvatar
    } else if (round) {
        imageLoaderConfig.placeholderRound
    } else {
        imageLoaderConfig.placeholder
    }

    load(url) {
        placeholder(placeholder)
        error(placeholder)

        if (round) {
            circleCrop()
        } else if (corners > 0) {
            // 圆角和 CenterCrop 不兼容，需同时设置
            transform(CenterCrop(), RoundedCorners(corners))
        }
    }
}

fun ImageView.load(url: Any?, block: RequestOptions.() -> Unit = {}) {
    if (ActivityUtils.isActivityAlive(this.context).not()) {
        return
    }

    val requestOptions = RequestOptions()
    block(requestOptions)
    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadBitmap(url: Any?, placeholder: Int = 0) {
    if (ActivityUtils.isActivityAlive(this.context).not()) {
        return
    }

    if (placeholder > 0) {
        this.setImageResource(placeholder)
    }
    Glide.with(this)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                this@loadBitmap.setImageBitmap(resource)
            }
        })
}
