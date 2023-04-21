package me.wcy.common.utils

/**
 * Created by wangchenyan.top on 2022/11/4.
 */
object UUID {
    fun getUUID(): String {
        return java.util.UUID.randomUUID().toString().replace("-", "")
    }

    fun getShortUUID(): String {
        return getUUID().take(8)
    }
}