package top.wangchenyan.common.utils

import android.view.View
import com.blankj.utilcode.util.KeyboardUtils

object KeyboardUtils {
    fun showSoftInputDelay(view: View, delay: Long = 300) {
        view.postDelayed({ KeyboardUtils.showSoftInput(view) }, delay)
    }
}