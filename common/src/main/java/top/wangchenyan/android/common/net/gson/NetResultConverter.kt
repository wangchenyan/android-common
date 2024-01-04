/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wangchenyan.android.common.net.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Converter
import top.wangchenyan.android.common.CommonApp
import top.wangchenyan.android.common.net.NetResult
import top.wangchenyan.android.common.net.gson.ResponseUtils.readJson
import java.io.IOException
import java.lang.reflect.Type

internal class NetResultConverter(
    private val gson: Gson,
    private val dataType: Type,
    private val removeNullValues: Boolean
) : Converter<ResponseBody, NetResult<*>> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): NetResult<*> {
        val respJson = value.readJson(gson, removeNullValues).asJsonObject
        val apiConfig = CommonApp.config.apiConfig
        val code = respJson.get(apiConfig.codeJsonNames)?.asInt ?: Int.MIN_VALUE
        val msg = respJson.get(apiConfig.msgJsonNames)?.asString ?: ""
        val total = respJson.get(apiConfig.totalJsonNames)?.asInt ?: 0
        return if (code == apiConfig.successCode) {
            val dataJson = respJson.get(apiConfig.dataJsonNames)
            val data: Any? = gson.fromJson(dataJson, dataType)
            NetResult(code, msg, data, total)
        } else {
            NetResult(code, msg, null, total)
        }
    }

    private fun JsonObject.get(memberNames: List<String>): JsonElement? {
        memberNames.forEach {
            if (has(it)) {
                return get(it)
            }
        }
        return null
    }
}