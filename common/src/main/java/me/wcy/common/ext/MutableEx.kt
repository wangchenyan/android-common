package me.wcy.common.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by wangchenyan.top on 2022/6/13.
 */

fun <T> MutableList<T>.toUnMutable(): List<T> = this

fun <T> MutableLiveData<T>.toUnMutable(): LiveData<T> = this

fun <T> MutableStateFlow<T>.toUnMutable(): StateFlow<T> = this