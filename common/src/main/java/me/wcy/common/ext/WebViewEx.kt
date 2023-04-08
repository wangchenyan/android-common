package me.wcy.common.ext

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.IntentUtils

/**
 * Created by wangchenyan.top on 2022/9/29.
 */

fun WebView.initForBrowser() {
    initCommon()
    isHorizontalScrollBarEnabled = true
    isVerticalScrollBarEnabled = true
    settings.useWideViewPort = true
    settings.setSupportZoom(true)
    settings.builtInZoomControls = false
    webViewClient = WebClient()
}

fun WebView.initForRichText() {
    initCommon()
    isHorizontalScrollBarEnabled = false
    isVerticalScrollBarEnabled = false
    settings.useWideViewPort = false
    settings.setSupportZoom(false)
    settings.builtInZoomControls = false
}

fun WebView.loadRichText(text: String) {
    val html = text.fixScroll()
    loadHtml(html)
}

fun WebView.loadHtml(html: String) {
    loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
}

@SuppressLint("SetJavaScriptEnabled")
private fun WebView.initCommon() {
    settings.domStorageEnabled = true
    settings.databaseEnabled = true
    settings.allowFileAccess = false
    settings.savePassword = false
    settings.javaScriptEnabled = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        removeJavascriptInterface("searchBoxJavaBridge_")
        removeJavascriptInterface("accessibility")
        removeJavascriptInterface("accessibilityTraversal")
    }
}

/**
 * 修复富文本页面有空白的问题
 */
private fun String.fixScroll(): String {
    val head = "<head>" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
            "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
            "</head>"
    return "<html>$head<body style='margin:0;padding:0'>$this</body></html>"
}

private class WebClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url == null) {
            return false
        }

        if (url.startsWith("http://", true)
            || url.startsWith("https://", true)
            || url.startsWith("ftp://", true)
        ) {
            view?.loadUrl(url)
        } else {
            val uri = Uri.parse(url)
            if (uri.scheme?.isNotEmpty() == true && uri.host?.isNotEmpty() == true) {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (IntentUtils.isIntentAvailable(intent)) {
                    kotlin.runCatching {
                        view?.context?.startActivity(intent)
                    }
                }
            }
        }

        return true
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        handler?.proceed()
    }
}