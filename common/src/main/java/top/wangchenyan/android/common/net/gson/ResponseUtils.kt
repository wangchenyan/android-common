package top.wangchenyan.android.common.net.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonIOException
import com.google.gson.stream.JsonToken
import top.wangchenyan.android.common.utils.GsonUtils.removeNullValues
import okhttp3.ResponseBody
import okio.IOException

/**
 * Created by wangchenyan.top on 2022/10/8.
 */
internal object ResponseUtils {
    @Throws(IOException::class)
    fun ResponseBody.readJson(
        gson: Gson,
        removeNullValues: Boolean
    ): JsonElement {
        this.use { body ->
            val reader = gson.newJsonReader(body.charStream())
            val adapter = gson.getAdapter(JsonElement::class.java)
            val element = adapter.read(reader)
            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            if (removeNullValues) {
                element.removeNullValues()
            }
            return element
        }
    }
}