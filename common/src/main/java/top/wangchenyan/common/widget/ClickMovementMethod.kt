package top.wangchenyan.common.widget

import android.text.Spannable
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewConfiguration
import android.widget.TextView

/**
 * Created by wangchenyan on 2021/10/7.
 */
class ClickMovementMethod : OnTouchListener {
    private var longClickCallback: LongClickCallback? = null
    private var comsumed = false

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (longClickCallback == null) {
            longClickCallback = LongClickCallback(v)
        }
        val widget = v as TextView
        // MovementMethod设为空，防止消费长按事件
        widget.movementMethod = null
        val text = widget.text
        val spannable = Spannable.Factory.getInstance().newSpannable(text)
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            val off = layout.getOffsetForHorizontal(line, x.toFloat())
            val link = spannable.getSpans(off, off, ClickableSpan::class.java)
            if (link.isNotEmpty()) {
                if (action == MotionEvent.ACTION_DOWN) {
                    comsumed = false
                    v.postDelayed(
                        {
                            longClickCallback?.run()
                            comsumed = true
                        },
                        ViewConfiguration.getLongPressTimeout().toLong()
                    )
                } else if (comsumed.not()) {
                    v.removeCallbacks(longClickCallback)
                    link[0].onClick(widget)
                }
                return true
            }
        } else if (action == MotionEvent.ACTION_CANCEL) {
            v.removeCallbacks(longClickCallback)
        }
        return false
    }

    private class LongClickCallback(private val view: View) : Runnable {
        override fun run() {
            // 找到能够消费长按事件的View
            var v: View? = view
            var consumed = v?.performLongClick() == true
            while (!consumed) {
                v = v?.parent as? View
                if (v == null) {
                    break
                }
                consumed = v.performLongClick()
            }
        }
    }

    companion object {
        fun newInstance(): ClickMovementMethod {
            return ClickMovementMethod()
        }
    }
}