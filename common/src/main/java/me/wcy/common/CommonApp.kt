package me.wcy.common

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.blankj.utilcode.util.Utils.OnAppStatusChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import me.wcy.common.ext.toUnMutable
import me.wcy.common.utils.filedownloader.FileDownloader

/**
 * Created by wcy on 2022/5/13.
 */
object CommonApp {
    private var _config: CommonConfig? = null

    val app: Application by lazy {
        Utils.getApp()
    }

    val appScope: CoroutineScope by lazy {
        MainScope()
    }

    val config: CommonConfig by lazy {
        if (_config == null) {
            throw IllegalStateException("CommonApp not init, please init first!")
        }
        _config!!
    }

    val test: Boolean by lazy { config.test }

    private val appForegroundInternal = MutableLiveData(false)
    val appForeground = appForegroundInternal.toUnMutable()

    fun init(config: CommonConfig) {
        _config = config
        FileDownloader.setupOnApplicationOnCreate(app)
        GsonUtils.setGsonDelegate(me.wcy.common.utils.GsonUtils.gson)
        AppUtils.registerAppStatusChangedListener(object : OnAppStatusChangedListener {
            override fun onForeground(activity: Activity?) {
                appForegroundInternal.value = true
            }

            override fun onBackground(activity: Activity?) {
                appForegroundInternal.value = false
            }
        })
    }
}