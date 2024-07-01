package top.wangchenyan.common.utils

import android.view.Gravity
import androidx.annotation.StringRes
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils.MODE
import top.wangchenyan.common.CommonApp

object ToastUtils {

    @JvmStatic
    fun show(text: CharSequence?) {
        showToast(false, text.toString())
    }

    @JvmStatic
    fun show(@StringRes resId: Int, vararg args: Any) {
        showToast(false, StringUtils.getString(resId), *args)
    }

    @JvmStatic
    fun showLong(text: CharSequence?) {
        showToast(true, text.toString())
    }

    @JvmStatic
    fun showLong(@StringRes resId: Int, vararg args: Any) {
        showToast(true, StringUtils.getString(resId), *args)
    }

    private fun showToast(isLong: Boolean, text: String, vararg args: Any) {
        if (text.isEmpty()) {
            return
        }
        val mode = if (CommonApp.config.isDarkMode()) MODE.LIGHT else MODE.DARK
        com.blankj.utilcode.util.ToastUtils.make()
            .setMode(mode)
            .setGravity(Gravity.CENTER, 0, 0)
            .setDurationIsLong(isLong)
            .show(text, *args)
    }
}