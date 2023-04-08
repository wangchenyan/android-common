package me.wcy.common.ext

import android.app.Activity
import android.content.Context
import androidx.viewbinding.ViewBinding

fun Activity?.setAlpha(alpha: Float) {
    if (this == null) {
        return
    }
    val lp = window.attributes
    lp.alpha = alpha
    window.attributes = lp
}

val ViewBinding.context: Context
    get() = root.context
