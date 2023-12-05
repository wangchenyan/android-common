package top.wangchenyan.android.common.ext

import top.wangchenyan.android.common.utils.ToastUtils

fun toast(text: CharSequence?) {
    if (text.isNullOrEmpty()) return
    ToastUtils.show(text)
}

fun toast(resId: Int, vararg args: Any) {
    ToastUtils.show(resId, *args)
}
