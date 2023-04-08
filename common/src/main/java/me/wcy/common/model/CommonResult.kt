package me.wcy.common.model

/**
 * Created by wangchenyan.top on 2022/8/31.
 */
data class CommonResult<T>(
    val code: Int,
    val msg: String?,
    val data: T?
) {
    fun isSuccess(): Boolean = (code == 200)

    fun isSuccessWithData(): Boolean = (code == 200 && data != null)

    fun getDataOrThrow(): T = data!!

    companion object {
        fun <T> success(data: T): CommonResult<T> {
            return CommonResult(200, null, data)
        }

        fun <T> fail(code: Int = -1, msg: String? = null): CommonResult<T> {
            val c = if (code == 200) -1 else code
            return CommonResult(c, msg, null)
        }
    }
}
