package me.wcy.common.ext

import java.util.Calendar

/**
 * Created by wangchenyan.top on 2023/1/2.
 */

fun Calendar.clearTime(): Calendar {
    return this.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}