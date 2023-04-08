package me.wcy.common.utils

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import me.wcy.common.model.CommonResult
import java.net.URLEncoder


object LaunchUtils {
    private const val WECHAT_PACKAGE_NAME = "com.tencent.mm"

    /**
     * 说明：使用浏览器打开
     */
    fun launchBrowser(context: Context?, url: String?) {
        if (context == null || url.isNullOrEmpty()) {
            return
        }
        kotlin.runCatching {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    fun launchGooglePlay(context: Context) {
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://play.google.com/store/apps/details?id=${context.packageName}"
                )
                setPackage("com.android.vending")
            }
            context.startActivity(intent)
        }
    }

    fun launchHuaweiStore(context: Context) {
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=${context.packageName}")
                setPackage("com.huawei.appmarket")
            }
            context.startActivity(intent)
        }
    }

    fun launchWechat(context: Context): Boolean {
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                component = ComponentName(WECHAT_PACKAGE_NAME, "com.tencent.mm.ui.LauncherUI")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(intent)
            return true
        }
        return false
    }

    fun launchWechatScan(context: Context): Boolean {
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                component = ComponentName(WECHAT_PACKAGE_NAME, "com.tencent.mm.ui.LauncherUI")
                putExtra("LauncherUI.From.Scaner.Shortcut", true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(intent)
            return true
        }
        return false
    }

    fun launchQQGroup(context: Context, groupId: String): CommonResult<Any> {
        val url =
            "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=${groupId}&card_type=group&source=qrcode"
        return try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            CommonResult.success(1)
        } catch (e: Throwable) {
            if (e is ActivityNotFoundException) {
                CommonResult.fail(msg = "未安装QQ")
            } else {
                CommonResult.fail(msg = e.localizedMessage)
            }
        }
    }

    fun launchAlipayWithUrl(context: Context, url: String): Boolean {
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse(
                "alipays://platformapi/startapp?saId=10000007&qrcode=" +
                        URLEncoder.encode(url, "UTF-8")
            )
            intent.data = uri
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return true
        }
        return false
    }
}