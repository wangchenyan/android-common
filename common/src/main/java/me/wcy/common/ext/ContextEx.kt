package me.wcy.common.ext

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.EntryPointAccessors

fun Activity?.setAlpha(alpha: Float) {
    if (this == null) {
        return
    }
    val lp = window.attributes
    lp.alpha = alpha
    window.attributes = lp
}

inline fun <reified T : Any> Application.accessEntryPoint(): T {
    return EntryPointAccessors.fromApplication(this, T::class.java)
}

fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this

        is ContextWrapper -> this.baseContext?.findActivity()

        else -> null
    }
}

fun Context.findLifecycleOwner(): LifecycleOwner? {
    return when (this) {
        is LifecycleOwner -> this

        is ContextWrapper -> this.baseContext?.findLifecycleOwner()

        else -> null
    }
}