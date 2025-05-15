package top.wangchenyan.common.insets

import android.app.Activity
import android.view.View
import android.view.View.OnAttachStateChangeListener
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.InsetsType

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
        if (isAttachedToWindow) {
            callback(getStatusBarHeight())
        } else {
            addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    callback(getStatusBarHeight())
                    removeOnAttachStateChangeListener(this)
                }

                override fun onViewDetachedFromWindow(v: View) {
                }
            })
        }
    }

    fun Activity.getNavigationBarHeight(): Int {
        val contentView = findViewById<View>(android.R.id.content)
        return contentView.getNavigationBarHeight()
    }

    fun View.getNavigationBarHeight(): Int {
        return getWindowInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }

    fun View.getNavigationBarHeight(callback: (Int) -> Unit) {
        if (isAttachedToWindow) {
            callback(getNavigationBarHeight())
        } else {
            addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    callback(getNavigationBarHeight())
                    removeOnAttachStateChangeListener(this)
                }

                override fun onViewDetachedFromWindow(v: View) {
                }
            })
        }
    }

    private fun View.getWindowInsets(@InsetsType typeMask: Int): Insets {
        val insets = ViewCompat.getRootWindowInsets(this) ?: return Insets.NONE
        return insets.getInsets(typeMask)
    }
}