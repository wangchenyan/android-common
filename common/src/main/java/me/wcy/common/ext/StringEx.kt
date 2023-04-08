package me.wcy.common.ext

import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.EncryptUtils

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
