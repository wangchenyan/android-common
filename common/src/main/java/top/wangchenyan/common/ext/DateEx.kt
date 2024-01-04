package top.wangchenyan.common.ext

import com.blankj.utilcode.util.TimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by wcy on 2020/12/29.
 */

val FORMAT_YMDHMS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
val FORMAT_YMDHM = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
val FORMAT_HMS = SimpleDateFormat("HH:mm:ss", Locale.CHINA)
val FORMAT_HM = SimpleDateFormat("HH:mm", Locale.CHINA)

fun String?.dateFriendly(format: DateFormat = FORMAT_YMDHM): String {
    if (this == null) {
        return ""
    }
    val date = TimeUtils.string2Date(this, format) ?: return this
    val millis = date.time
    if (System.currentTimeMillis() < millis) {
        return this
    }
    return TimeUtils.getFriendlyTimeSpanByNow(millis)
}

fun Long?.dateFriendly(): String {
    if (this == null) {
        return ""
    }
    if (System.currentTimeMillis() < this) {
        return TimeUtils.millis2String(this)
    }
    return TimeUtils.getFriendlyTimeSpanByNow(this)
}

fun Long?.ymdhm(): String {
    if (this == null) {
        return ""
    }
    return TimeUtils.millis2String(this, FORMAT_YMDHM)
}

fun Long?.hm(): String {
    if (this == null) {
        return ""
    }
    return TimeUtils.millis2String(this, FORMAT_HM)
}
