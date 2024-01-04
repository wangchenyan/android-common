package top.wangchenyan.common.widget.decoration

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.wangchenyan.common.R

/**
 * Created by wangchenyan.top on 2023/7/1.
 */
class MarginDividerDecoration(
    context: Context,
    private val marginStart: Int = 0,
    private val marginEnd: Int = marginStart,
    @DrawableRes
    private val drawableRes: Int = 0,
    @ColorInt
    private val bgColor: Int = Color.TRANSPARENT,
    private val bgMargin: Int = 0,
) : RecyclerView.ItemDecoration() {
    /**
     * @return the [Drawable] for this divider.
     */
    private var drawable: Drawable? = null

    private val bgDrawable by lazy {
        GradientDrawable().apply {
            color = ColorStateList.valueOf(bgColor)
        }
    }

    private val mBounds = Rect()

    /**
     * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
     * [LinearLayoutManager].
     *
     * @param context Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
     */
    init {
        if (drawableRes != 0) {
            drawable = ContextCompat.getDrawable(context, drawableRes)
        } else {
            val a = context.obtainStyledAttributes(ATTRS)
            drawable = a.getDrawable(0)
            if (drawable == null) {
                Log.w(
                    TAG, "@android:attr/listDivider was not set in the theme used for this "
                            + "DividerItemDecoration. Please set that attribute all call setDrawable()"
                )
                drawable = ContextCompat.getDrawable(context, R.drawable.common_ic_divider_line)
            }
            a.recycle()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || drawable == null) {
            return
        }
        drawVertical(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(child.translationY)
            val top = bottom - drawable!!.intrinsicHeight
            // 兼容带背景的Item
            if (bgColor != Color.TRANSPARENT) {
                bgDrawable.setBounds(left + bgMargin, top, right - bgMargin, bottom)
                bgDrawable.draw(canvas)
            }
            drawable!!.setBounds(left + marginStart, top, right - marginEnd, bottom)
            drawable!!.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (drawable == null) {
            outRect[0, 0, 0] = 0
            return
        }
        outRect[0, 0, 0] = drawable!!.intrinsicHeight
    }

    companion object {
        private const val TAG = "DividerItem"
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}