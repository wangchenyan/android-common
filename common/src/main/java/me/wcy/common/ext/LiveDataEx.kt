package me.wcy.common.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by wangchenyan.top on 2023/1/5.
 */

fun <T> LiveData<T>.observeLazy(owner: LifecycleOwner, observer: Observer<in T?>) {
    observe(owner, object : LazyObserver<T>() {
        override fun onLazyChanged(it: T) {
            observer.onChanged(it)
        }
    })
}

/**
 * 首次注册不需要回调数据
 */
abstract class LazyObserver<T> : Observer<T> {
    private var isFirst = true

    final override fun onChanged(it: T) {
        if (isFirst) {
            isFirst = false
        } else {
            onLazyChanged(it)
        }
    }

    abstract fun onLazyChanged(it: T)
}