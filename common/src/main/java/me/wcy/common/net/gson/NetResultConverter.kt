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
package me.wcy.common.net.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import me.wcy.common.net.NetResult
import me.wcy.common.net.gson.ResponseUtils.readJson
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.io.Serializable
import java.lang.reflect.Type

internal class NetResultConverter(
    private val gson: Gson,
    private val dataType: Type,
    private val removeNullValues: Boolean
) : Converter<ResponseBody, NetResult<*>> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): NetResult<*> {
        val jsonElement = value.readJson(gson, removeNullValues)
        val adapter = gson.getAdapter(RawNetResult::class.java)
        val result = adapter.fromJsonTree(jsonElement)
        return if (result.isSuccess()) {
            val data: Any? = gson.fromJson(result.data, dataType)
            NetResult(result.code, result.msg, data, result.total)
        } else {
            NetResult(result.code, result.msg, null, result.total)
        }
    }

    data class RawNetResult(
        @SerializedName("code") var code: Int = Integer.MIN_VALUE,
        @SerializedName("msg", alternate = ["message"]) var msg: String? = null,
        @SerializedName("data", alternate = ["result"]) var data: JsonElement? = null,
        @SerializedName("total") val total: Int = 0,
    ) : Serializable {
        fun isSuccess(): Boolean = (code == 200)
    }
}