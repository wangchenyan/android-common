package top.wangchenyan.common.insets

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding

/**
 * Created by wangchenyan.top on 2025/5/14.
 */
class WindowInsetsManagerImpl(activity: Activity) : WindowInsetsManager {
    private val contentView: View = activity.findViewById(android.R.id.content)
    private val windowInsetsController: WindowInsetsControllerCompat =
        WindowCompat.getInsetsController(activity.window, contentView)
    private val overlayDrawable = WindowInsetsOverlayDrawable()

    override var fillStatusBar: Boolean = false
        set(value) {
            field = value
            contentView.requestLayout()
        }

    override var fillNavBar: Boolean = true
        set(value) {
            field = value
            contentView.requestLayout()
        }

    override var fillIme: Boolean = false
        set(value) {
            field = value
            contentView.requestLayout()
        }

    override var fillDisplayCutout: Boolean = false
        set(value) {
            field = value
            contentView.requestLayout()
        }

    override var statusBarColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            overlayDrawable.setStatusColor(value)
            contentView.invalidate()
        }

    override var navBarColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            overlayDrawable.setNavColor(value)
            contentView.invalidate()
        }

    override var displayCutoutColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            overlayDrawable.setDisplayCutoutColor(value)
            contentView.invalidate()
        }

    override var statusBarTextDarkStyle: Boolean = true
        set(value) {
            field = value
            windowInsetsController.isAppearanceLightStatusBars = value
        }

    override var navBarButtonDarkStyle: Boolean = true
        set(value) {
            field = value
            windowInsetsController.isAppearanceLightNavigationBars = value
        }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity.window.isNavigationBarContrastEnforced = false
        }

        contentView.overlay.add(overlayDrawable)
        contentView.doOnLayout {
            overlayDrawable.setBounds(0, 0, it.width, it.height)
        }

        ViewCompat.setOnApplyWindowInsetsListener(contentView) { v, insets ->
            var mask = 0
            if (fillStatusBar) {
                mask = mask or WindowInsetsCompat.Type.statusBars()
            }
            if (fillNavBar) {
                mask = mask or WindowInsetsCompat.Type.navigationBars()
            }
            if (fillIme) {
                mask = mask or WindowInsetsCompat.Type.ime()
            }
            if (fillDisplayCutout) {
                mask = mask or WindowInsetsCompat.Type.displayCutout()
            }
            insets.getInsets(mask).let {
                contentView.updatePadding(
                    left = it.left,
                    top = it.top,
                    right = it.right,
                    bottom = it.bottom
                )
                overlayDrawable.updateInsets(
                    left = it.left,
                    top = it.top,
                    right = it.right,
                    bottom = it.bottom
                )
            }
            insets
        }
        ViewCompat.requestApplyInsets(contentView)

        navBarButtonDarkStyle = navBarButtonDarkStyle
    }
}