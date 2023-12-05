package top.wangchenyan.android.common.widget

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import top.wangchenyan.android.common.CommonConfigDsl
import top.wangchenyan.android.common.R

/**
 * Created by wangchenyan.top on 2023/8/17.
 */
class TitleLayoutConfig internal constructor(builder: Builder) {
    val isStatusBarDarkFontWhenAuto: () -> Boolean

    @get:ColorRes
    val textColorBlack: () -> Int

    @get:ColorRes
    val textColorWhite: () -> Int

    @get:ColorRes
    val textColorAuto: () -> Int

    @get:DrawableRes
    val backIcon: () -> Int

    @get:DrawableRes
    val closeIcon: () -> Int

    val isTitleCenter: Boolean

    init {
        isStatusBarDarkFontWhenAuto = builder.isStatusBarDarkFontWhenAuto
        textColorBlack = builder.textColorBlack
        textColorWhite = builder.textColorWhite
        textColorAuto = builder.textColorAuto
        backIcon = builder.backIcon
        closeIcon = builder.closeIcon
        isTitleCenter = builder.isTitleCenter
    }

    @CommonConfigDsl
    class Builder {
        var isStatusBarDarkFontWhenAuto: () -> Boolean = { true }

        @get:ColorRes
        var textColorBlack: () -> Int = { R.color.common_text_h1_color }

        @get:ColorRes
        var textColorWhite: () -> Int = { R.color.white }

        @get:ColorRes
        var textColorAuto: () -> Int = { R.color.common_text_h1_color }

        @get:DrawableRes
        var backIcon: () -> Int = { R.drawable.common_ic_title_back }

        @get:DrawableRes
        var closeIcon: () -> Int = { R.drawable.common_ic_title_close }

        var isTitleCenter = true

        fun build(): TitleLayoutConfig {
            return TitleLayoutConfig(this)
        }
    }
}