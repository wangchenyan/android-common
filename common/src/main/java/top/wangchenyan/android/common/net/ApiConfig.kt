package top.wangchenyan.android.common.net

import top.wangchenyan.android.common.CommonConfigDsl

/**
 * Created by wangchenyan.top on 2023/8/17.
 */
class ApiConfig internal constructor(builder: Builder) {
    val codeJsonName: String
    val msgJsonName: String
    val dataJsonName: String
    val totalJsonName: String
    val successCode: Int
    val authInvalidCodes: Set<Int>
    val onAuthInvalid: suspend () -> Unit
    val beforeApiCall: suspend () -> Unit

    init {
        codeJsonName = builder.codeJsonName
        msgJsonName = builder.msgJsonName
        dataJsonName = builder.dataJsonName
        totalJsonName = builder.totalJsonName
        successCode = builder.successCode
        authInvalidCodes = builder.authInvalidCodes
        onAuthInvalid = builder.onAuthInvalid
        beforeApiCall = builder.beforeApiCall
    }

    @CommonConfigDsl
    class Builder(val onAuthInvalid: suspend () -> Unit) {
        var codeJsonName: String = "code"
        var msgJsonName: String = "msg"
        var dataJsonName: String = "data"
        var totalJsonName: String = "total"
        var successCode: Int = 200
        var authInvalidCodes: Set<Int> = setOf(401)
        var beforeApiCall: suspend () -> Unit = {}

        fun build(): ApiConfig {
            return ApiConfig(this)
        }
    }
}
