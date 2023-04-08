package me.wcy.common.net

import com.google.gson.annotations.SerializedName
import me.wcy.common.model.CommonResult
import java.io.Serializable

data class NetResult<T>(
    @SerializedName("code") var code: Int = Integer.MIN_VALUE,
    @SerializedName("msg", alternate = ["message"]) var msg: String? = null,
    @SerializedName("data", alternate = ["result"]) var data: T? = null,
    @SerializedName("total") val total: Int = 0,
) : Serializable {
    fun isSuccess(): Boolean = (code == 200)

    fun isSuccessWithData(): Boolean = (code == 200 && data != null)

    fun getDataOrThrow(): T = data!!

    fun toCommonResult(): CommonResult<T> {
        return CommonResult(code, msg, data)
    }
}
