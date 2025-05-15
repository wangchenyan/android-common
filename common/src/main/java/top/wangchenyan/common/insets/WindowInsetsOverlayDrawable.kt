package top.wangchenyan.common.insets

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 * Created by wangchenyan.top on 2025/5/14.
 */
class WindowInsetsOverlayDrawable : Drawable() {
    private val paint = Paint()
    private var statusColor = Color.TRANSPARENT
    private var navColor = Color.TRANSPARENT
    private var displayCutoutColor = Color.TRANSPARENT
    private var leftInsets = 0
    private var topInsets = 0
    private var rightInsets = 0
    private var bottomInsets = 0
    private val rect = Rect()

    fun setStatusColor(@ColorInt color: Int) {
        this.statusColor = color
    }

    fun setNavColor(@ColorInt color: Int) {
        this.navColor = color
    }

    fun setDisplayCutoutColor(@ColorInt color: Int) {
        this.displayCutoutColor = color
    }

    fun updateInsets(left: Int, top: Int, right: Int, bottom: Int) {
        this.leftInsets = left
        this.topInsets = top
        this.rightInsets = right
        this.bottomInsets = bottom
    }

    override fun draw(canvas: Canvas) {
        val bounds = getBounds()
        // left
        if (leftInsets > 0) {
            rect.set(0, 0, leftInsets, bounds.height())
            paint.setColor(this.displayCutoutColor)
            canvas.drawRect(rect, paint)
        }
        // top
        if (topInsets > 0) {
            rect.set(0, 0, bounds.width(), topInsets)
            paint.setColor(this.statusColor)
            canvas.drawRect(rect, paint)
        }
        // right
        if (rightInsets > 0) {
            rect.set(bounds.width() - rightInsets, 0, bounds.width(), bounds.height())
            paint.setColor(this.displayCutoutColor)
            canvas.drawRect(rect, paint)
        }
        // bottom
        if (bottomInsets > 0) {
            rect.set(0, bounds.height() - bottomInsets, bounds.width(), bounds.height())
            paint.setColor(this.navColor)
            canvas.drawRect(rect, paint)
        }
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("PixelFormat.OPAQUE", "android.graphics.PixelFormat"),
    )
    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}