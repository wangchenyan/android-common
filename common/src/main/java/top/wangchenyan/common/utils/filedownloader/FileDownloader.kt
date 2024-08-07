package top.wangchenyan.common.utils.filedownloader

import android.app.Application
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadLargeFileListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by wangchenyan.top on 2022/9/27.
 */
object FileDownloader {

    fun setupOnApplicationOnCreate(application: Application) {
        com.liulishuo.filedownloader.FileDownloader.setupOnApplicationOnCreate(application)
            .connectionCreator(HttpsFileDownloadUrlConnection.Creator())
    }

    suspend fun download(
        url: String,
        path: String,
        retry: Int = 3,
    ): String {
        return suspendCoroutine { continuation ->
            download(url, path, object : DownloadListener {
                override fun onProgress(current: Long, total: Long) {
                }

                override fun onSuccess(path: String) {
                    continuation.resume(path)
                }

                override fun onFail(t: Throwable?) {
                    continuation.resumeWithException(t ?: RuntimeException("unknown"))
                }
            }, retry)
        }
    }

    fun download(
        url: String,
        path: String,
        listener: DownloadListener,
        retry: Int = 3,
    ) {
        com.liulishuo.filedownloader.FileDownloader.getImpl()
            .create(url)
            .setPath(path)
            .setAutoRetryTimes(retry)
            .setListener(object : FileDownloadLargeFileListener() {
                override fun pending(task: BaseDownloadTask?, soFarBytes: Long, totalBytes: Long) {
                }

                override fun progress(task: BaseDownloadTask?, soFarBytes: Long, totalBytes: Long) {
                    listener.onProgress(soFarBytes, totalBytes)
                }

                override fun completed(task: BaseDownloadTask?) {
                    listener.onSuccess(path)
                }

                override fun paused(task: BaseDownloadTask?, soFarBytes: Long, totalBytes: Long) {
                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    listener.onFail(e)
                }

                override fun warn(task: BaseDownloadTask?) {
                }
            })
            .start()
    }

    interface DownloadListener {
        fun onProgress(current: Long, total: Long)
        fun onSuccess(path: String)
        fun onFail(t: Throwable?)
    }
}