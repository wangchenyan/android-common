package me.wcy.common.net

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.wcy.common.CommonApp

/**
 * Created by wcy on 2020/11/5.
 */
object ApiCaller {
    const val TAG = "ApiCaller"

    suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> NetResult<T>): NetResult<T> {
        return withContext(Dispatchers.IO) {
            CommonApp.config.apiCaller.beforeApiCall()
            val res: NetResult<T>
            try {
                res = call()
            } catch (e: Throwable) {
                Log.e(TAG, "request error", e)
                return@withContext ApiException.build(e).toResult<T>()
            }

            if (res.code == ApiException.CODE_AUTH_INVALID) {
                Log.e(TAG, "request auth invalid")
                CommonApp.config.apiCaller.onAuthInvalid()
            }
            return@withContext res
        }
    }

    fun <T> ApiException.toResult(): NetResult<T> {
        return NetResult(code, message)
    }

    interface ApiCallerEvent {
        fun onAuthInvalid()

        suspend fun beforeApiCall()
    }
}