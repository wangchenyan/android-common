package top.wangchenyan.android.common.net

import top.wangchenyan.android.common.CommonConfigDsl

/**
 * Created by wangchenyan.top on 2023/8/17.
 */
class ApiConfig internal constructor(builder: Builder) {
    val codeJsonNames: List<String>
    val msgJsonNames: List<String>
    val dataJsonNames: List<String>
    val totalJsonNames: List<String>
    val successCode: Int
    val authInvalidCodes: Set<Int>
    val onAuthInvalid: suspend () -> Unit
    val beforeApiCall: suspend () -> Unit

    init {
        codeJsonNames = builder.codeJsonNames
        msgJsonNames = builder.msgJsonNames
        dataJsonNames = builder.dataJsonNames
        totalJsonNames = builder.totalJsonNames
        successCode = builder.successCode
        authInvalidCodes = builder.authInvalidCodes
        onAuthInvalid = builder.onAuthInvalid
        beforeApiCall = builder.beforeApiCall
    }

    @CommonConfigDsl
    class Builder(val onAuthInvalid: suspend () -> Unit) {
        var codeJsonNames: List<String> = listOf("code")
        var msgJsonNames: List<String> = listOf("msg", "message")
        var dataJsonNames: List<String> = listOf("data", "result")
        var totalJsonNames: List<String> = listOf("total")
        var successCode: Int = 200
        var authInvalidCodes: Set<Int> = setOf(401)
        var beforeApiCall: suspend () -> Unit = {}

        fun build(): ApiConfig {
            return ApiConfig(this)
        }
    }
}
