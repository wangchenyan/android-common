package top.wangchenyan.common.utils

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject

/**
 * Created by wcy on 2021/2/24.
 */
object GsonUtils {
    val gson by lazy { Gson() }
    val prettyGson by lazy { GsonBuilder().setPrettyPrinting().create() }

    fun toJson(obj: Any?): String? {
        kotlin.runCatching {
            return GsonUtils.toJson(obj)
        }
        return null
    }

    fun <T> fromJson(json: String?, type: Class<T>): T? {
        kotlin.runCatching {
            return GsonUtils.fromJson(json, type)
        }
        return null
    }

    inline fun <reified T> fromJsonList(json: String): List<T>? {
        return fromJsonList(json, T::class.java)
    }

    fun <T> fromJsonList(json: String, clazz: Class<T>): List<T>? {
        try {
            val jsonArray = gson.fromJson(json, JsonArray::class.java)
            return fromJsonList(jsonArray, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun <T> fromJsonList(jsonArray: JsonArray, clazz: Class<T>): List<T>? {
        try {
            val list = mutableListOf<T>()
            for (item in jsonArray) {
                val bean = gson.fromJson(item, clazz)
                list.add(bean)
            }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun toPrettyJson(any: Any): String {
        return prettyGson.toJson(any)
    }

    fun JsonElement.removeNullValues() {
        when (this) {
            is JsonObject -> this.removeObjectNullValues()
            is JsonArray -> this.removeArrayNullValues()
        }
    }

    private fun JsonObject.removeObjectNullValues() {
        val iterator = this.keySet().iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            when (val json = this[key]) {
                is JsonNull -> iterator.remove()
                else -> json.removeNullValues()
            }
        }
    }

    private fun JsonArray.removeArrayNullValues() {
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            when (val element = iterator.next()) {
                is JsonNull -> iterator.remove()
                else -> element.removeNullValues()
            }
        }
    }
}