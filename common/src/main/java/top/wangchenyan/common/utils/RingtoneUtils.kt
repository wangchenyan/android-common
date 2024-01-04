package top.wangchenyan.common.utils

import android.media.RingtoneManager
import top.wangchenyan.common.CommonApp

/**
 * Created by wangchenyan.top on 2021/10/19.
 */
object RingtoneUtils {

    fun playRingtone() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val rt = RingtoneManager.getRingtone(CommonApp.app, uri)
        rt.play()
    }
}