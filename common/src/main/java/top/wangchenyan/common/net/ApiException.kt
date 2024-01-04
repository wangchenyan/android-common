package top.wangchenyan.common.net

import com.blankj.utilcode.util.StringUtils
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import top.wangchenyan.common.R
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by wcy on 2020/11/4.
 */
open class ApiException(
    val code: Int,
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {
    companion object {
        const val CODE_NET_ERROR = 400
        const val CODE_TIMEOUT = 408
        const val CODE_JSON_PARSE_ERROR = 4000
        const val CODE_SERVER_ERROR = 500

        fun build(e: Throwable): ApiException {
            return when (e) {
                is HttpException -> {
                    ApiException(
                        e.code(),
                        "${StringUtils.getString(R.string.common_net_error)}(${e.code()},${e.message()})"
                    )
                }

                is UnknownHostException -> {
                    ApiException(
                        CODE_NET_ERROR,
                        StringUtils.getString(R.string.common_net_unavailable)
                    )
                }

                is ConnectTimeoutException,
                is SocketTimeoutException -> {
                    ApiException(CODE_TIMEOUT, StringUtils.getString(R.string.common_net_timeout))
                }

                is IOException -> {
                    ApiException(
                        CODE_NET_ERROR,
                        "${StringUtils.getString(R.string.common_net_error)}(${e.message})"
                    )
                }

                is JsonParseException,
                is JSONException -> {
                    // Json解析失败
                    ApiException(
                        CODE_JSON_PARSE_ERROR,
                        StringUtils.getString(R.string.common_net_data_parse_error)
                    )
                }

                else -> {
                    ApiException(
                        CODE_SERVER_ERROR,
                        "${StringUtils.getString(R.string.common_net_system_error)}(${e.message})"
                    )
                }
            }
        }
    }
}
