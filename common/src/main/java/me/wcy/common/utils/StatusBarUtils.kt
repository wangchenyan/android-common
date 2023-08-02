package me.wcy.common.utils

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import com.gyf.immersionbar.ImmersionBar

/**
 * Created by wcy on 2019/6/5.
 */
object StatusBarUtils {

    /**
     * 获取状态栏高度
     * 如果是刘海屏，返回刘海高度
     */
    fun getStatusBarHeight(activity: Activity): Int {
        return if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            && ImmersionBar.hasNotchScreen(activity)
        ) {
            ImmersionBar.getNotchHeight(activity)
        } else {
            ImmersionBar.getStatusBarHeight(activity)
        }
    }

    /**
     * 获取状态栏高度（准确）
     * 如果是刘海屏，返回刘海高度
     */
    fun getStatusBarHeight(activity: Activity, callback: (height: Int) -> Unit) {
        if (activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            callback(ImmersionBar.getStatusBarHeight(activity))
            return
        }
        activity.window.decorView.post {
            if (ImmersionBar.hasNotchScreen(activity)) {
                val height = ImmersionBar.getNotchHeight(activity)
                callback(height)
            } else {
                callback(ImmersionBar.getStatusBarHeight(activity))
            }
        }
    }

    @JvmStatic
    fun isSupportStatusBarTransparent(): Boolean {
        return AndroidVersionUtils.isAboveOrEqual6()
    }
}