package top.wangchenyan.android_common

import android.app.Application
import me.wcy.common.CommonApp

/**
 * Created by wangchenyan.top on 2023/4/16.
 */
class SimpleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CommonApp.init(CommonConfigImpl())
    }
}