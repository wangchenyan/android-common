package top.wangchenyan.common.utils

import com.blankj.utilcode.util.TimeUtils

/**
 * Created by wangchenyan.top on 2023/4/10.
 */
object DateUtils {
    /**
     * 格式：yyyy
     */
    const val Y = "yyyy"

    /**
     * 格式：yyyy-MM-dd
     */
    const val YMD = "yyyy-MM-dd"

    /**
     * 格式：yyyy-MM-dd HH:mm
     */
    const val YMDHM = "yyyy-MM-dd HH:mm"

    /**
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    const val YMDHMS = "yyyy-MM-dd HH:mm:ss"

    /**
     * Return the friendly time span by now.
     * @return the friendly time span by now
     *
     * - 如果小于 1 秒钟内，显示刚刚
     * - 如果在 1 分钟内，显示 XXX秒前
     * - 如果在 1 小时内，显示 XXX分钟前
     * - 如果在 1 小时外的今天内，显示今天15:32
     * - 如果是昨天的，显示昨天15:32
     * - 其余显示，2016-10-15
     */
    fun formatTimeFriendly(time: String, format: String = YMDHMS): String {
        val date = TimeUtils.string2Date(time, format) ?: return time
        return formatTimeFriendly(date.time)
    }

    /**
     * Return the friendly time span by now.
     * @return the friendly time span by now
     *
     * - 如果小于 1 秒钟内，显示刚刚
     * - 如果在 1 分钟内，显示 XXX秒前
     * - 如果在 1 小时内，显示 XXX分钟前
     * - 如果在 1 小时外的今天内，显示今天15:32
     * - 如果是昨天的，显示昨天15:32
     * - 其余显示，2016-10-15
     */
    fun formatTimeFriendly(millis: Long): String {
        if (System.currentTimeMillis() < millis) {
            return TimeUtils.millis2String(millis, YMD)
        }
        return TimeUtils.getFriendlyTimeSpanByNow(millis)
    }
}