package top.wangchenyan.android.common.widget

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.text.inSpans
import top.wangchenyan.android.common.utils.AndroidVersionUtils

/**
 * Created by wangchenyan.top on 2023/2/26.
 */
object CustomSpan {

    /**
     * 添加带样式的文本
     *
     * @param color 颜色
     * @param size 大小，单位px，设置点击事件后无效
     * @param isBold 粗体
     */
    fun SpannableStringBuilder.appendStyle(
        text: CharSequence,
        @ColorInt color: Int? = null,
        @Dimension size: Int? = null,
        isBold: Boolean = false,
        isShowUnderline: Boolean = false,
        onClick: ((View) -> Unit)? = null
    ) {
        if (onClick != null) {
            inSpans(CustomClickSpan(color, isBold, isShowUnderline, onClick)) {
                append(text)
            }
        } else if (size != null) {
            inSpans(CustomSizeSpan(size, color, isBold, isShowUnderline)) {
                append(text)
            }
        } else if (color != null) {
            inSpans(CustomTextSpan(color, isBold, isShowUnderline)) {
                append(text)
            }
        } else {
            append(text)
        }
    }

    fun SpannableStringBuilder.appendImage(
        context: Context,
        @DrawableRes resId: Int,
        @ColorInt tint: Int? = null
    ) {
        val drawable = ContextCompat.getDrawable(context, resId) ?: return
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        if (tint != null) {
            drawable.setTint(tint)
        }
        val alignment = if (AndroidVersionUtils.isAboveOrEqual10()) {
            ImageSpan.ALIGN_CENTER
        } else {
            ImageSpan.ALIGN_BOTTOM
        }
        inSpans(ImageSpan(drawable, alignment)) {
            append("$")
        }
    }

    private class CustomTextSpan(
        @ColorInt private val color: Int,
        private val isBold: Boolean = false,
        private val isShowUnderline: Boolean = false,
    ) : ForegroundColorSpan(color) {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isFakeBoldText = isBold
            ds.isUnderlineText = isShowUnderline
        }
    }

    private class CustomSizeSpan(
        @Dimension size: Int,
        @ColorInt private val color: Int? = null,
        private val isBold: Boolean = false,
        private val isShowUnderline: Boolean = false,
    ) : AbsoluteSizeSpan(size) {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            if (color != null) {
                ds.color = color
            }
            ds.isFakeBoldText = isBold
            ds.isUnderlineText = isShowUnderline
        }
    }

    private class CustomClickSpan(
        @ColorInt private val color: Int? = null,
        private val isBold: Boolean = false,
        private val isShowUnderline: Boolean = false,
        private val onClick: ((View) -> Unit)
    ) : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            if (color != null) {
                ds.color = color
            }
            ds.isFakeBoldText = isBold
            ds.isUnderlineText = isShowUnderline
        }

        override fun onClick(widget: View) {
            onClick.invoke(widget)
        }
    }
}