package me.wcy.common.ext

import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.EncryptUtils
import me.wcy.common.utils.DateUtils

/**
 * Created by wangchenyan.top on 2022/7/14.
 */

fun View.getString(@StringRes resId: Int): String {
    return context.getString(resId)
}

fun ViewBinding.getString(@StringRes resId: Int): String {
    return context.getString(resId)
}

/**
 * 转MD5
 */
fun String.md5(lowercase: Boolean = true): String =
    EncryptUtils.encryptMD5ToString(this).run {
        if (lowercase) {
            lowercase()
        } else {
            uppercase()
        }
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
fun String?.formatTimeFriendly(format: String = DateUtils.YMDHMS): String {
    this ?: return ""
    return DateUtils.formatTimeFriendly(this, format)
}
