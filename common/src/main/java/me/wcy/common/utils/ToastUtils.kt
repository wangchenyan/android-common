package me.wcy.common.utils

import android.view.Gravity
import androidx.annotation.StringRes
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils.MODE
import me.wcy.common.CommonApp

object ToastUtils {

    @JvmStatic
    fun show(text: CharSequence?) {
        showToast(text.toString())
    }

    @JvmStatic
    fun show(@StringRes resId: Int, vararg args: Any) {
        showToast(StringUtils.getString(resId), *args)
    }

    private fun showToast(text: String, vararg args: Any) {
        if (text.isEmpty()) {
            return
        }
        val mode = if (CommonApp.config.isDarkMode) MODE.LIGHT else MODE.DARK
        com.blankj.utilcode.util.ToastUtils.make()
            .setMode(mode)
            .setGravity(Gravity.CENTER, 0, 0)
            .show(text, *args)
    }
}