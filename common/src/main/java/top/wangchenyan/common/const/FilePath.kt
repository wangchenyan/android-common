package top.wangchenyan.common.const

import top.wangchenyan.common.BuildConfig
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.utils.ServerTime
import top.wangchenyan.common.utils.UUID
import java.io.File

/**
 * Created by wangchenyan.top on 2022/9/24.
 */
internal object FilePath {
    private val IMAGE = "image".assembleExternalCachePath()

    fun getCacheImageFilePath(): String {
        return "$IMAGE${File.separator}image_${UUID.getShortUUID()}_${ServerTime.currentTimeMillis()}.jpg"
    }

    private fun String.assembleExternalCachePath(): String {
        return "${CommonApp.app.externalCacheDir}" +
                "${File.separator}${BuildConfig.LIBRARY_PACKAGE_NAME}" +
                "${File.separator}$this"
    }
}