package me.wcy.common.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import me.wcy.common.CommonApp
import me.wcy.common.R

/**
 * Created by wangchenyan on 2018/8/23.
 */

fun ImageView?.load(url: Any?) {
    load(url, avatar = false, round = false, corners = 0)
}

fun ImageView?.load(url: Any?, round: Boolean) {
    load(url, avatar = false, round = round, corners = 0)
}

fun ImageView?.load(url: Any?, corners: Int) {
    load(url, avatar = false, round = false, corners = corners)
}

fun ImageView?.loadAvatar(url: Any?) {
    load(url, avatar = true, round = false, corners = 0)
}

fun ImageView?.loadGif(url: Any?) {
    if (this == null) {
        return
    }

    if (ActivityUtils.isActivityAlive(this.context).not()) {
        return
    }

    Glide.with(this)
        .asGif()
        .load(url)
        .into(this)
}

private fun ImageView?.load(url: Any?, avatar: Boolean, round: Boolean, corners: Int) {
    if (this == null) {
        return
    }

    if (ActivityUtils.isActivityAlive(this.context).not()) {
        return
    }

    val imageLoaderConfig = CommonApp.config.imageLoader
    val placeholder = if (avatar) {
        imageLoaderConfig.placeholderAvatar
    } else if (round) {
        imageLoaderConfig.placeholderRound
    } else {
        imageLoaderConfig.placeholder
    }
    setImageResource(placeholder)
    var builder = Glide.with(this)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(placeholder)
                .error(placeholder)
        )
    if (avatar || round) {
        builder = builder.apply(RequestOptions.circleCropTransform())
    } else if (corners > 0) {
        // 圆角和 CenterCrop 不兼容，需同时设置
        builder = builder.apply(RequestOptions().transform(CenterCrop(), RoundedCorners(corners)))
    }
    builder.into(this)
}

fun ImageView?.loadBitmap(url: Any?, placeholder: Int = 0) {
    if (this == null) {
        return
    }

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

interface ImageLoaderConfig {
    @get:DrawableRes
    val placeholder: Int
        get() = R.drawable.common_bg_image_placeholder

    @get:DrawableRes
    val placeholderRound: Int
        get() = R.drawable.base_bg_image_placeholder_round

    @get:DrawableRes
    val placeholderAvatar: Int
        get() = R.drawable.base_bg_image_placeholder_round
}
