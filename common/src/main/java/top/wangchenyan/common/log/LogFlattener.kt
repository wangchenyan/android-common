package top.wangchenyan.common.log

import com.blankj.utilcode.util.TimeUtils
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.flattener.Flattener
import com.elvishew.xlog.flattener.Flattener2
import java.util.Date

/**
 * Created by wangchenyan.top on 2023/5/15.
 */
class LogFlattener : Flattener, Flattener2 {
    override fun flatten(logLevel: Int, tag: String?, message: String?): CharSequence {
        return flatten(System.currentTimeMillis(), logLevel, tag, message)
    }

    override fun flatten(
        timeMillis: Long,
        logLevel: Int,
        tag: String?,
        message: String?
    ): CharSequence {
        val time = TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
            .format(Date(timeMillis))
        return "$time ${LogLevel.getShortLevelName(logLevel)}/$message"
    }
}