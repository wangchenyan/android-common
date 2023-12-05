package top.wangchenyan.android.common.utils

import android.os.SystemClock
import android.util.Log
import com.blankj.utilcode.util.TimeUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Date

object ServerTime : Interceptor {
    private const val TAG = "ServerTime"
    private var initServerTime: Long = 0
    private var initBootTime: Long = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        init(response)
        return response
    }

    @Synchronized
    fun init(response: Response) {
        if (isInit()) return
        val date = response.headers["Date"] ?: response.headers["date"]
        Log.i(TAG, "init with date: $date")
        if (date?.isNotEmpty() == true) {
            kotlin.runCatching {
                init(Date(date).time)
            }
        }
    }

    @Synchronized
    fun init(serverTime: Long) {
        if (isInit()) return
        Log.i(
            TAG, "init with serverTime: $serverTime, " +
                    "format: ${TimeUtils.millis2String(serverTime)}"
        )
        if (serverTime <= 0) return
        initServerTime = serverTime
        initBootTime = SystemClock.elapsedRealtime()
        Log.i(TAG, "init success")
    }

    fun isInit(): Boolean {
        return initServerTime > 0
    }

    fun currentTimeMillis(): Long {
        return if (isInit()) {
            initServerTime + (SystemClock.elapsedRealtime() - initBootTime)
        } else {
            System.currentTimeMillis()
        }
    }
}