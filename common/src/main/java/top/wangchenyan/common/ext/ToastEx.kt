package top.wangchenyan.common.ext

import top.wangchenyan.common.utils.ToastUtils

fun toast(text: CharSequence?) {
    if (text.isNullOrEmpty()) return
    ToastUtils.show(text)
}

fun toast(resId: Int, vararg args: Any) {
    ToastUtils.show(resId, *args)
}

fun toastLong(text: CharSequence?) {
    if (text.isNullOrEmpty()) return
    ToastUtils.showLong(text)
}

fun toastLong(resId: Int, vararg args: Any) {
    ToastUtils.showLong(resId, *args)
}
