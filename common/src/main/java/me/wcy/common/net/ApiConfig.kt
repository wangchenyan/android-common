package me.wcy.common.net

import me.wcy.common.CommonConfigDsl

/**
 * Created by wangchenyan.top on 2023/8/17.
 */
class ApiConfig internal constructor(builder: Builder) {
    val authInvalidCodes: Set<Int>

    val onAuthInvalid: suspend () -> Unit

    val beforeApiCall: suspend () -> Unit

    init {
        authInvalidCodes = builder.authInvalidCodes
        onAuthInvalid = builder.onAuthInvalid
        beforeApiCall = builder.beforeApiCall
    }

    @CommonConfigDsl
    class Builder(val onAuthInvalid: suspend () -> Unit) {
        var authInvalidCodes: Set<Int> = setOf(401)
        var beforeApiCall: suspend () -> Unit = {}

        fun build(): ApiConfig {
            return ApiConfig(this)
        }
    }
}
