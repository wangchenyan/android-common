package top.wangchenyan.common.insets

import android.app.Activity
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.core.view.doOnAttach

/**
 * Created by wangchenyan.top on 2025/5/14.
 */
object WindowInsetsUtils {

    fun Activity.getStatusBarHeight(): Int {
        val contentView = findViewById<View>(android.R.id.content)
        return contentView.getStatusBarHeight()
    }

    fun View.getStatusBarHeight(): Int {
        return getWindowInsets(WindowInsetsCompat.Type.statusBars()).top
    }

    fun View.getStatusBarHeight(callback: (Int) -> Unit) {
        doOnAttach { callback(getStatusBarHeight()) }
    }

    fun Activity.getNavigationBarHeight(): Int {
        val contentView = findViewById<View>(android.R.id.content)
        return contentView.getNavigationBarHeight()
    }

    fun View.getNavigationBarHeight(): Int {
        return getWindowInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }

    fun View.getNavigationBarHeight(callback: (Int) -> Unit) {
        doOnAttach { callback(getNavigationBarHeight()) }
    }

    private fun View.getWindowInsets(@InsetsType typeMask: Int): Insets {
        val insets = ViewCompat.getRootWindowInsets(this) ?: return Insets.NONE
        return insets.getInsets(typeMask)
    }
}