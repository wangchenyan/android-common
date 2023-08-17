package me.wcy.common.widget

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import me.wcy.common.CommonConfigDsl
import me.wcy.common.R

/**
 * Created by wangchenyan.top on 2023/8/17.
 */
class TitleLayoutConfig internal constructor(builder: Builder) {
    val isStatusBarDarkFontWhenAuto: Boolean

    @get:ColorRes
    val textColorBlack: Int

    @get:ColorRes
    val textColorWhite: Int

    @get:ColorRes
    val textColorAuto: Int

    @get:DrawableRes
    val backIcon: Int

    init {
        isStatusBarDarkFontWhenAuto = builder.isStatusBarDarkFontWhenAuto
        textColorBlack = builder.textColorBlack
        textColorWhite = builder.textColorWhite
        textColorAuto = builder.textColorAuto
        backIcon = builder.backIcon
    }

    @CommonConfigDsl
    class Builder {
        var isStatusBarDarkFontWhenAuto: Boolean = true

        @get:ColorRes
        var textColorBlack: Int = R.color.common_text_h1_color

        @get:ColorRes
        var textColorWhite: Int = R.color.white

        @get:ColorRes
        var textColorAuto: Int = R.color.common_text_h1_color

        @get:DrawableRes
        var backIcon: Int = R.drawable.common_ic_title_back

        fun build(): TitleLayoutConfig {
            return TitleLayoutConfig(this)
        }
    }
}