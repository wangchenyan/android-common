package top.wangchenyan.common.utils

import android.os.Build
import com.blankj.utilcode.util.AppUtils

/**
 * Created by wangchenyan.top on 2023/8/2.
 */
object AndroidVersionUtils {

    fun isAboveOrEqual(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }

    fun isTargetAboveOrEqual(version: Int): Boolean {
        return AppUtils.getAppTargetSdkVersion() >= version
    }

    fun isAboveOrEqual6(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.M)
    }

    fun isAboveOrEqual7(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.N)
    }

    fun isAboveOrEqual8(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.O)
    }

    fun isAboveOrEqual9(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.P)
    }

    fun isAboveOrEqual10(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.Q)
    }

    fun isAboveOrEqual11(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.R)
    }

    fun isAboveOrEqual12(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.S)
    }

    fun isAboveOrEqual13(): Boolean {
        return isAboveOrEqual(Build.VERSION_CODES.TIRAMISU)
    }

    fun isTargetAboveOrEqual13(): Boolean {
        return isTargetAboveOrEqual(Build.VERSION_CODES.TIRAMISU)
    }
}