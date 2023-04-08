package me.wcy.common.ext

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.InputType
import android.text.method.LinkMovementMethod
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isNotEmpty
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doAfterTextChanged
import com.blankj.utilcode.util.ClickUtils
import me.wcy.common.R

/**
 * @author wcy
 * @date 2018/6/13
 */

fun EditText?.inputMoney(digit: Int = 2) {
    this?.apply {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        doAfterTextChanged {
            post {
                if (text.contains(".")) {
                    val index = text.indexOf(".")
                    if (index >= 0 && text.length - index - 1 > digit) {
                        val selection = this@inputMoney.selectionStart
                        this@inputMoney.setText(text.substring(0, index + digit + 1))
                        this@inputMoney.setSelection(selection.coerceAtMost(this@inputMoney.length()))
                    }
                }
            }
        }
    }
}

fun EditText?.setEditable(editable: Boolean) {
    this?.apply {
        isEnabled = editable
        isFocusable = editable
        isFocusableInTouchMode = editable
    }
}

fun EditText?.selectEnd() {
    this?.setSelection(length())
}

fun TextView?.setLink() {
    this?.highlightColor = Color.TRANSPARENT
    this?.movementMethod = LinkMovementMethod.getInstance()
}

fun NestedScrollView.fixClick() {
    if (this.isNotEmpty()) {
        this.getChildAt(0).setOnClickListener {
            this.callOnClick()
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
fun TextView?.setSelectable(onClickListener: View.OnClickListener? = null) {
    this ?: return
    setTextIsSelectable(true)
    highlightColor = context.getColorEx(R.color.common_theme_color)
    if (onClickListener != null) {
        /**
         * setTextIsSelectable 会拦截点击事件，使用 GestureDetector 处理
         */
        val detector = GestureDetectorCompat(context, GestureDetector.SimpleOnGestureListener())
        detector.setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                onClickListener.onClick(this@setSelectable)
                return false
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                return false
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                return false
            }
        })
        setOnTouchListener { v, event ->
            detector.onTouchEvent(event)
            false
        }
    }
}

fun View.setHeight(height: Int) {
    val params = layoutParams
    params?.let {
        it.height = height
        this.layoutParams = it
    }
}

fun View.setDebouncingClickListener(listener: View.OnClickListener) {
    ClickUtils.applySingleDebouncing(this, listener)
}