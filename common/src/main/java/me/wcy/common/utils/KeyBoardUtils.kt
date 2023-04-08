package me.wcy.common.utils

import android.view.View
import com.blankj.utilcode.util.KeyboardUtils

object KeyBoardUtils {
    fun showSoftInputDelay(view: View, delay: Long = 300) {
        view.postDelayed({ KeyboardUtils.showSoftInput(view) }, delay)
    }
}