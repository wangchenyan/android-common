package top.wangchenyan.common

import top.wangchenyan.common.ext.ImageLoaderConfig
import top.wangchenyan.common.net.ApiConfig
import top.wangchenyan.common.widget.TitleLayoutConfig

/**
 * Created by wangchenyan.top on 2023/2/22.
 */
class CommonConfig internal constructor(builder: Builder) {
    val test: Boolean

    val isDarkMode: () -> Boolean

    val titleLayoutConfig: TitleLayoutConfig

    val imageLoaderConfig: ImageLoaderConfig

    val apiConfig: ApiConfig

    init {
        test = builder.test
        isDarkMode = builder.isDarkMode
        titleLayoutConfig = builder.titleLayoutConfig
        imageLoaderConfig = builder.imageLoaderConfig
        apiConfig = builder.apiConfig
    }

    @CommonConfigDsl
    class Builder {
        var test: Boolean = false
        var isDarkMode: () -> Boolean = { false }
        var titleLayoutConfig: TitleLayoutConfig = TitleLayoutConfig.Builder().build()
        var imageLoaderConfig: ImageLoaderConfig = ImageLoaderConfig.Builder().build()
        var apiConfig: ApiConfig = ApiConfig.Builder {}.build()

        fun titleLayoutConfig(block: TitleLayoutConfig.Builder.() -> Unit) {
            val builder = TitleLayoutConfig.Builder()
            builder.block()
            titleLayoutConfig = builder.build()
        }

        fun imageLoaderConfig(block: ImageLoaderConfig.Builder.() -> Unit) {
            val builder = ImageLoaderConfig.Builder()
            builder.block()
            imageLoaderConfig = builder.build()
        }

        fun apiConfig(onAuthInvalid: suspend () -> Unit, block: ApiConfig.Builder.() -> Unit) {
            val builder = ApiConfig.Builder(onAuthInvalid)
            builder.block()
            apiConfig = builder.build()
        }

        fun build(): CommonConfig {
            return CommonConfig(this)
        }
    }
}