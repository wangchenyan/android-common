package top.wangchenyan.common

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.blankj.utilcode.util.Utils.OnAppStatusChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import top.wangchenyan.common.ext.toUnMutable
import top.wangchenyan.common.utils.filedownloader.FileDownloader

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

    private val _appForeground = MutableLiveData(AppUtils.isAppForeground())
    val appForeground = _appForeground.toUnMutable()

    fun init(block: CommonConfig.Builder.() -> Unit) {
        val builder = CommonConfig.Builder()
        builder.block()
        _config = builder.build()
        FileDownloader.setupOnApplicationOnCreate(app)
        GsonUtils.setGsonDelegate(top.wangchenyan.common.utils.GsonUtils.gson)
        AppUtils.registerAppStatusChangedListener(object : OnAppStatusChangedListener {
            override fun onForeground(activity: Activity?) {
                _appForeground.value = true
            }

            override fun onBackground(activity: Activity?) {
                _appForeground.value = false
            }
        })
    }
}