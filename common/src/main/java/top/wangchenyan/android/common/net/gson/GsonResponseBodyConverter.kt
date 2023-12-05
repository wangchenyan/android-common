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
import com.google.gson.TypeAdapter
import top.wangchenyan.android.common.net.gson.ResponseUtils.readJson
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

internal class GsonResponseBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>,
    private val removeNullValues: Boolean
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val jsonElement = value.readJson(gson, removeNullValues)
        return adapter.fromJsonTree(jsonElement)
    }
}