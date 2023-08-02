package me.wcy.common.utils

import android.os.Build

/**
 * Created by wangchenyan.top on 2023/8/2.
 */
object AndroidVersionUtils {

    fun isAboveOrEqual(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
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
}