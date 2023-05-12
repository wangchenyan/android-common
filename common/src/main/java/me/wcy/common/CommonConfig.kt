package me.wcy.common

import androidx.annotation.DrawableRes
import me.wcy.common.ext.ImageLoaderConfig
import me.wcy.common.net.ApiCallerEvent
import me.wcy.common.widget.TitleLayout

/**
 * Created by wangchenyan.top on 2023/2/22.
 */
interface CommonConfig {
    val test: Boolean

    val isDarkMode: Boolean

    val res: ResConfig

    val title: TitleLayout.TitleLayoutConfig

    val apiCaller: ApiCallerEvent

    val imageLoader: ImageLoaderConfig
        get() = object : ImageLoaderConfig {}

    interface ResConfig {

        @get:DrawableRes
        val logoRoundRes: Int

        @get:DrawableRes
        val notificationIconRes: Int
    }
}