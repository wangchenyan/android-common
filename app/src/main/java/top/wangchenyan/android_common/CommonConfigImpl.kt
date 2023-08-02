package top.wangchenyan.android_common

import me.wcy.common.CommonConfig
import me.wcy.common.ext.ImageLoaderConfig
import me.wcy.common.net.ApiCallerEvent
import me.wcy.common.widget.TitleLayout

/**
 * Created by wangchenyan.top on 2023/4/10.
 */
class CommonConfigImpl : CommonConfig {
    override val test: Boolean
        get() = true
    override val isDarkMode: Boolean
        get() = false
    override val res: CommonConfig.ResConfig
        get() = object : CommonConfig.ResConfig {
            override val logoRoundRes: Int
                get() = R.mipmap.ic_launcher_round
            override val notificationIconRes: Int
                get() = R.mipmap.ic_launcher
        }
    override val title: TitleLayout.TitleLayoutConfig
        get() = object : TitleLayout.TitleLayoutConfig {
            override val isStatusBarDarkFontWhenAuto: Boolean
                get() = true
            override val textColorBlack: Int
                get() = R.color.black
            override val textColorWhite: Int
                get() = R.color.white
            override val textColorAuto: Int
                get() = R.color.black
            override val backIcon: Int
                get() = R.drawable.common_ic_title_back
        }
    override val apiCaller: ApiCallerEvent
        get() = object : ApiCallerEvent {
            override suspend fun onAuthInvalid() {

            }
        }
    override val imageLoader: ImageLoaderConfig
        get() = object : ImageLoaderConfig {
            override val placeholder: Int
                get() = R.mipmap.ic_launcher
        }
}