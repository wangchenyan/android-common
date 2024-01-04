package top.wangchenyan.common.model

import top.wangchenyan.common.CommonApp

/**
 * Created by wangchenyan.top on 2022/8/31.
 */
data class CommonResult<T>(
    val code: Int,
    val msg: String?,
    val data: T?
) {
    fun isSuccess(): Boolean = (code == getApiConfig().successCode)

    fun isSuccessWithData(): Boolean = (code == getApiConfig().successCode && data != null)

    fun getDataOrThrow(): T = data!!

    companion object {
        fun <T> success(data: T): CommonResult<T> {
            return CommonResult(getApiConfig().successCode, null, data)
        }

        fun <T> fail(code: Int = Int.MIN_VALUE, msg: String? = null): CommonResult<T> {
            val c = if (code == getApiConfig().successCode) Int.MIN_VALUE else code
            return CommonResult(c, msg, null)
        }

        private fun getApiConfig() = CommonApp.config.apiConfig
    }
}
