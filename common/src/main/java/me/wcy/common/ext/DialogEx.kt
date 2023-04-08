package me.wcy.common.ext

import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.StringUtils
import me.wcy.common.R
import me.wcy.common.widget.dialog.BottomItemsDialogBuilder
import me.wcy.common.widget.dialog.CenterDialogBuilder

/**
 * Created by wangchenyan.top on 2022/9/24.
 */

fun Fragment.showSingleDialog(
    title: CharSequence = StringUtils.getString(R.string.common_tips),
    message: CharSequence = "",
    buttonText: CharSequence = StringUtils.getString(R.string.common_confirm),
    showClose: Boolean = false,
    onButtonClick: () -> Unit = {}
) = context.showSingleDialog(title, message, buttonText, showClose, onButtonClick)

fun Context?.showSingleDialog(
    title: CharSequence = StringUtils.getString(R.string.common_tips),
    message: CharSequence = "",
    buttonText: CharSequence = StringUtils.getString(R.string.common_confirm),
    showClose: Boolean = false,
    onButtonClick: () -> Unit = {}
) {
    this ?: return
    CenterDialogBuilder(this)
        .title(title)
        .message(message)
        .isMessageCenter(true)
        .buttonText(buttonText)
        .isShowCloseView(showClose)
        .onButtonClickListener { dialog, which ->
            onButtonClick()
        }
        .cancelable(false)
        .build()
        .show()
}

fun Fragment.showConfirmDialog(
    title: CharSequence = StringUtils.getString(R.string.common_tips),
    message: CharSequence = "",
    confirmButton: CharSequence = StringUtils.getString(R.string.common_confirm),
    cancelButton: CharSequence = StringUtils.getString(R.string.common_cancel),
    isMessageCenter: Boolean = true,
    cancelable: Boolean = false,
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
) = context.showConfirmDialog(
    title,
    message,
    confirmButton,
    cancelButton,
    isMessageCenter,
    cancelable,
    onCancelClick,
    onConfirmClick
)

fun Context?.showConfirmDialog(
    title: CharSequence = StringUtils.getString(R.string.common_tips),
    message: CharSequence = "",
    confirmButton: CharSequence = StringUtils.getString(R.string.common_confirm),
    cancelButton: CharSequence = StringUtils.getString(R.string.common_cancel),
    isMessageCenter: Boolean = true,
    cancelable: Boolean = false,
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
) {
    this ?: return
    CenterDialogBuilder(this)
        .title(title)
        .message(message)
        .isMessageCenter(isMessageCenter)
        .buttonText(confirmButton, cancelButton)
        .onButtonClickListener { dialog, which ->
            if (which == 0) {
                onConfirmClick()
            } else {
                onCancelClick()
            }
        }
        .cancelable(cancelable)
        .build()
        .show()
}

fun Fragment.showBottomItemsDialog(
    items: List<CharSequence>,
    selectedPosition: Int = -1,
    cancelable: Boolean = true,
    onClickListener: DialogInterface.OnClickListener? = null,
) = context.showBottomItemsDialog(items, selectedPosition, cancelable, onClickListener)

fun Context?.showBottomItemsDialog(
    items: List<CharSequence>,
    selectedPosition: Int = -1,
    cancelable: Boolean = true,
    onClickListener: DialogInterface.OnClickListener? = null,
) {
    this ?: return
    BottomItemsDialogBuilder(this)
        .items(items)
        .selectedPosition(selectedPosition)
        .cancelable(cancelable)
        .onClickListener(onClickListener)
        .build()
        .show()
}