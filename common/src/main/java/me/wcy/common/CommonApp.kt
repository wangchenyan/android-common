package me.wcy.common

import android.app.Application
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import me.wcy.common.utils.filedownloader.FileDownloader

/**
 * Created by wcy on 2022/5/13.
 */
object CommonApp {
    private var _config: CommonConfig? = null

    @JvmStatic
    val app: Application = Utils.getApp()

    @JvmStatic
    val config: CommonConfig by lazy {
        if (_config == null) {
            throw IllegalStateException("CommonApp not init, please init first!")
        }
        _config!!
    }

    @JvmStatic
    val test: Boolean by lazy { config.test }

    fun init(config: CommonConfig) {
        _config = config
        FileDownloader.setupOnApplicationOnCreate(app)
        GsonUtils.setGsonDelegate(me.wcy.common.utils.GsonUtils.gson)
    }
}