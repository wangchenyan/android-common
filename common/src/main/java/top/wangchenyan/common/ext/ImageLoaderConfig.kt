package top.wangchenyan.common.ext

import androidx.annotation.DrawableRes
import top.wangchenyan.common.CommonConfigDsl
import top.wangchenyan.common.R

/**
 * Created by wangchenyan.top on 2023/8/17.
 */
class ImageLoaderConfig internal constructor(builder: Builder) {
    @get:DrawableRes
    val placeholder: Int

    @get:DrawableRes
    val placeholderRound: Int

    @get:DrawableRes
    val placeholderAvatar: Int

    init {
        placeholder = builder.placeholder
        placeholderRound = builder.placeholderRound
        placeholderAvatar = builder.placeholderAvatar
    }

    @CommonConfigDsl
    class Builder {
        @get:DrawableRes
        var placeholder: Int = R.drawable.common_bg_image_placeholder

        @get:DrawableRes
        var placeholderRound: Int = R.drawable.common_bg_image_placeholder_round

        @get:DrawableRes
        var placeholderAvatar: Int = R.drawable.common_bg_image_placeholder_round

        fun build(): ImageLoaderConfig {
            return ImageLoaderConfig(this)
        }
    }
}
