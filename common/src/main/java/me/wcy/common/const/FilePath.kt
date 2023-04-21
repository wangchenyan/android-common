package me.wcy.common.const

import me.wcy.common.BuildConfig
import me.wcy.common.CommonApp
import me.wcy.common.utils.ServerTime
import me.wcy.common.utils.UUID
import java.io.File

/**
 * Created by wangchenyan.top on 2022/9/24.
 */
internal object FilePath {
    private val IMAGE = "image".assembleExternalCachePath()

    fun getCacheImageFilePath(): String {
        return "${IMAGE}${File.separator}image_${UUID.getShortUUID()}_${ServerTime.currentTimeMillis()}.jpg"
    }

    private fun String.assembleExternalCachePath(): String {
        return "${CommonApp.app.externalCacheDir}" +
                "${File.separator}${BuildConfig.LIBRARY_PACKAGE_NAME}" +
                "${File.separator}$this"
    }
}