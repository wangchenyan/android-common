package me.wcy.common.utils

import com.blankj.utilcode.util.TimeUtils
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by wangchenyan.top on 2022/12/18.
 */
object TimeUtils {

    fun getFriendlyTimeSpanByNowFix(time: String): String {
        return getFriendlyTimeSpanByNowFix(
            time,
            TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss")
        )
    }

    fun getFriendlyTimeSpanByNowFix(time: String, format: DateFormat): String {
        return getFriendlyTimeSpanByNowFix(TimeUtils.string2Millis(time, format))
    }

    fun getFriendlyTimeSpanByNowFix(date: Date): String {
        return getFriendlyTimeSpanByNowFix(date.time)
    }

    fun getFriendlyTimeSpanByNowFix(millis: Long): String {
        val now = System.currentTimeMillis()
        val span = now - millis
        if (span < 0) return TimeUtils.millis2String(millis)
        return TimeUtils.getFriendlyTimeSpanByNow(millis)
    }

    fun getNextMonth(day: Int = 1): Date {
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        if (month == Calendar.UNDECIMBER) {
            year += 1
            month = Calendar.JANUARY
        }
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}