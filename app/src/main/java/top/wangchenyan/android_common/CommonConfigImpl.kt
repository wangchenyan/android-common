package top.wangchenyan.android_common

import me.wcy.common.CommonConfig
import me.wcy.common.ext.ImageLoaderConfig
import me.wcy.common.net.ApiCaller
import me.wcy.common.widget.TitleLayout

/**
 * Created by wangchenyan.top on 2023/4/10.
 */
class CommonConfigImpl : CommonConfig {
    override val test: Boolean
        get() = TODO("Not yet implemented")
    override val isDarkMode: Boolean
        get() = TODO("Not yet implemented")
    override val res: CommonConfig.ResConfig
        get() = TODO("Not yet implemented")
    override val title: TitleLayout.TitleLayoutConfig
        get() = TODO("Not yet implemented")
    override val apiCaller: ApiCaller.ApiCallerEvent
        get() = TODO("Not yet implemented")
    override val imageLoader: ImageLoaderConfig
        get() = object : ImageLoaderConfig {
            override val placeholder: Int
                get() = R.mipmap.ic_launcher
        }
}