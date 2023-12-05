package top.wangchenyan.android.common.utils

import android.content.Context
import android.os.Vibrator
import top.wangchenyan.android.common.CommonApp

/**
 * Created by wangchenyan.top on 2021/9/28.
 */
object VibrateUtils {

    fun vibrate(time: Long = 300L) {
        val vibratorService =
            CommonApp.app.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibratorService.vibrate(time)
    }
}