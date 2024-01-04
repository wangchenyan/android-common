package top.wangchenyan.common.utils.filedownloader

import com.liulishuo.filedownloader.connection.FileDownloadConnection
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCreator
import java.io.IOException
import java.net.URL
import java.net.URLConnection
import javax.net.ssl.HttpsURLConnection

/**
 * The FileDownloadConnection implemented using [URLConnection].
 * Ignore hostname verify
 */
class HttpsFileDownloadUrlConnection : FileDownloadUrlConnection {

    @Throws(IOException::class)
    constructor(url: URL, configuration: Configuration?) : super(url, configuration)

    constructor(originUrl: String, configuration: Configuration?) : super(originUrl, configuration)

    init {
        val connection = mConnection
        if (connection is HttpsURLConnection) {
            connection.setHostnameVerifier { hostname, session -> true }
        }
    }

    class Creator @JvmOverloads constructor(private val mConfiguration: Configuration? = null) :
        ConnectionCreator {

        @Throws(IOException::class)
        fun create(url: URL): FileDownloadConnection {
            return HttpsFileDownloadUrlConnection(url, mConfiguration)
        }

        @Throws(IOException::class)
        override fun create(originUrl: String): FileDownloadConnection {
            return HttpsFileDownloadUrlConnection(originUrl, mConfiguration)
        }
    }
}