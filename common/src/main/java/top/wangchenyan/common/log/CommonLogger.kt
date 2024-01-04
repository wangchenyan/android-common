package top.wangchenyan.common.log

import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.NeverCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator

/**
 * Created by wangchenyan.top on 2023/5/15.
 */
class CommonLogger(type: String, dir: String) : ILogger {
    private val logger by lazy {
        XLog.tag(type)
            .printers(
                FilePrinter.Builder(dir) // 指定保存日志文件的路径
                    // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                    .fileNameGenerator(object : DateFileNameGenerator() {
                        override fun generateFileName(logLevel: Int, timestamp: Long): String {
                            return super.generateFileName(logLevel, timestamp) + ".log"
                        }
                    })
                    // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                    .backupStrategy(NeverBackupStrategy())
                    // 指定日志文件清除策略，默认为 NeverCleanStrategy()
                    .cleanStrategy(NeverCleanStrategy())
                    // 指定日志平铺器，默认为 DefaultFlattener
                    .flattener(LogFlattener())
                    .build(),
                AndroidPrinter(true)
            )
            .build()
    }

    override fun v(tag: String, msg: String?) {
        logger.v("$tag: $msg")
    }

    override fun v(tag: String, msg: String?, tr: Throwable?) {
        logger.v("$tag: $msg", tr)
    }

    override fun d(tag: String, msg: String?) {
        logger.d("$tag: $msg")
    }

    override fun d(tag: String, msg: String?, tr: Throwable?) {
        logger.d("$tag: $msg", tr)
    }

    override fun i(tag: String, msg: String?) {
        logger.i("$tag: $msg")
    }

    override fun i(tag: String, msg: String?, tr: Throwable?) {
        logger.i("$tag: $msg", tr)
    }

    override fun w(tag: String, msg: String?) {
        logger.w("$tag: $msg")
    }

    override fun w(tag: String, msg: String?, tr: Throwable?) {
        logger.w("$tag: $msg", tr)
    }

    override fun e(tag: String, msg: String?) {
        logger.e("$tag: $msg")
    }

    override fun e(tag: String, msg: String?, tr: Throwable?) {
        logger.e("$tag: $msg", tr)
    }

    companion object {
        init {
            XLog.init()
        }
    }
}