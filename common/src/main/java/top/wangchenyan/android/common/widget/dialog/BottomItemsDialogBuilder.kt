package top.wangchenyan.android.common.widget.dialog

import android.content.Context
import android.content.DialogInterface

/**
 * Created by wangchenyan.top on 2022/9/29.
 */
class BottomItemsDialogBuilder(internal val context: Context) {
    internal var items: List<CharSequence>? = null
    internal var selectedPosition: Int = -1
    internal var isCancelable: Boolean = true
    internal var onClickListener: DialogInterface.OnClickListener? = null

    fun items(value: List<CharSequence>?) = apply {
        items = value
    }

    fun items(vararg value: CharSequence) = apply {
        items = value.asList()
    }

    fun selectedPosition(value: Int) = apply {
        selectedPosition = value
    }

    fun cancelable(value: Boolean) = apply {
        isCancelable = value
    }

    fun onClickListener(value: DialogInterface.OnClickListener?) = apply {
        onClickListener = value
    }

    fun build(): BottomItemsDialog {
        return BottomItemsDialog(this)
    }
}