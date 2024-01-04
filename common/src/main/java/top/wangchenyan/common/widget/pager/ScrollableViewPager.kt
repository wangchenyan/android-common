package top.wangchenyan.common.widget.pager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by wcy on 2017/11/25.
 */
class ScrollableViewPager : ViewPager {
    private var isScrollable = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setScrollable(isScrollable: Boolean) {
        this.isScrollable = isScrollable
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return isScrollable && super.onTouchEvent(ev)
        } catch (e: Exception) {
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return isScrollable && super.onInterceptTouchEvent(ev)
        } catch (e: Exception) {
        }
        return false
    }
}
