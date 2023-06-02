package me.wcy.common

import me.wcy.common.net.ApiCallerEvent
import me.wcy.common.widget.TitleLayout

/**
 * Created by wangchenyan.top on 2023/5/31.
 */
class SimpleConfig : CommonConfig {
    override val test: Boolean
        get() = false
    override val isDarkMode: Boolean
        get() = false
    override val res: CommonConfig.ResConfig
        get() = object : CommonConfig.ResConfig {
            override val logoRoundRes: Int
                get() = 0
            override val notificationIconRes: Int
                get() = 0
        }
    override val title: TitleLayout.TitleLayoutConfig
        get() = object : TitleLayout.TitleLayoutConfig {
            override val isStatusBarDarkFontWhenAuto: Boolean
                get() = false
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
}