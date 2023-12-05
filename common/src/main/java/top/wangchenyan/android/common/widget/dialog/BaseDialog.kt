package top.wangchenyan.android.common.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils

/**
 * 安全的 Dialog
 * Created by wcy on 2019/7/7.
 */
open class BaseDialog : Dialog {

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun show() {
        if (ActivityUtils.isActivityAlive(context)) {
            kotlin.runCatching {
                super.show()
            }
        } else {
            Log.w(TAG, "dialog can not show with context $context")
        }
    }

    override fun dismiss() {
        kotlin.runCatching {
            KeyboardUtils.hideSoftInput(window)
            super.dismiss()
        }
    }

    companion object {
        private const val TAG = "BaseDialog"
    }
}