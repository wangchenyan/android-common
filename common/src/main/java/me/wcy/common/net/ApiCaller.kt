package me.wcy.common.net

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.wcy.common.CommonApp

/**
 * Created by wcy on 2020/11/5.
 */

const val TAG = "ApiCaller"

suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> NetResult<T>): NetResult<T> {
    return withContext(Dispatchers.IO) {
        CommonApp.config.apiConfig.beforeApiCall()
        val res: NetResult<T> = try {
            call()
        } catch (e: Throwable) {
            Log.e(TAG, "request error", e)
            ApiException.build(e).toResult()
        }

        val config = CommonApp.config.apiConfig

        if (res.code in config.authInvalidCodes) {
            Log.e(TAG, "request auth invalid")
            config.onAuthInvalid()
        }
        return@withContext res
    }
}

fun <T> ApiException.toResult(): NetResult<T> {
    return NetResult(code, message)
}