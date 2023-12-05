package me.wcy.common.net

import me.wcy.common.CommonApp
import me.wcy.common.model.CommonResult
import java.io.Serializable

data class NetResult<T>(
    var code: Int = Integer.MIN_VALUE,
    var msg: String? = null,
    var data: T? = null,
    val total: Int = 0,
) : Serializable {
    fun isSuccess(): Boolean = (code == getApiConfig().successCode)

    fun isSuccessWithData(): Boolean = (code == getApiConfig().successCode && data != null)

    fun getDataOrThrow(): T = data!!

    fun toCommonResult(): CommonResult<T> {
        return CommonResult(code, msg, data)
    }

    private fun getApiConfig() = CommonApp.config.apiConfig
}
