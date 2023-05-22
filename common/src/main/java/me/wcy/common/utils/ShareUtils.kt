package me.wcy.common.utils

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.UriUtils
import me.wcy.common.CommonApp
import me.wcy.common.ext.toast
import me.wcy.common.permission.Permissioner

/**
 * Created by wangchenyan.top on 2022/6/25.
 */
object ShareUtils {
    private val CN_WX_FRIEND =
        ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
    private val CN_WX_MOMENTS =
        ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI")
    private val CN_WX_FAVORITE =
        ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.AddFavoriteUI")

    // QQ分享会报 RiskWare/Android.QQshare 病毒
    // private val CN_QQ =
    //     ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity")

    private const val PKG_WEIBO = "com.sina.weibo"
    private val CN_WEIBO: ComponentName? by lazy {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        val packageManager: PackageManager = CommonApp.app.packageManager
        val matches =
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        matches.forEach {
            val pkgName = it.activityInfo.applicationInfo.packageName
            if (PKG_WEIBO == pkgName) {
                return@lazy ComponentName(PKG_WEIBO, it.activityInfo.name)
            }
        }
        return@lazy null
    }

    sealed class Platform(val cn: ComponentName?, val appName: String) {
        object WechatFriend : Platform(CN_WX_FRIEND, "微信")
        object WechatMoments : Platform(CN_WX_MOMENTS, "微信")

        // object QQ : Platform(CN_QQ, "QQ")

        object Weibo : Platform(CN_WEIBO, "微博")
    }

    fun shareText(
        context: Context,
        platform: Platform,
        text: String,
        callback: ((Boolean) -> Unit)? = null,
    ) {
        if (platform == Platform.WechatMoments) {
            shareImage(
                context,
                ImageUtils.getBitmap(AppUtils.getAppIconId()),
                platform.cn,
                platform.appName,
                bundleOf(Pair("Kdescription", text)),
                callback = callback,
            )
        } else {
            shareText(context, text, platform.cn, platform.appName, callback = callback)
        }
    }

    fun shareImage(
        context: Context,
        platform: Platform,
        bitmap: Bitmap,
        callback: ((Boolean) -> Unit)? = null,
    ) {
        shareImage(context, bitmap, platform.cn, platform.appName, callback = callback)
    }

    fun saveImage2Album(context: Context, bitmap: Bitmap, callback: ((Boolean) -> Unit)? = null) {
        Permissioner.requestStoragePermission(context) { granted, shouldRationale ->
            if (granted) {
                val result = ImageUtils.save2Album(bitmap, Bitmap.CompressFormat.PNG)
                if (result != null) {
                    toast("保存成功")
                    callback?.invoke(true)
                } else {
                    toast("保存失败")
                    callback?.invoke(false)
                }
            } else {
                callback?.invoke(false)
            }
        }
    }

    private fun shareImage(
        context: Context,
        bitmap: Bitmap,
        cn: ComponentName? = null,
        name: String? = null,
        extras: Bundle? = null,
        callback: ((Boolean) -> Unit)? = null,
    ) {
        Permissioner.requestStoragePermission(context) { granted, shouldRationale ->
            if (granted) {
                val file = ImageUtils.save2Album(bitmap, Bitmap.CompressFormat.PNG)
                if (file == null || file.exists().not()) {
                    toast("图片保存失败")
                    callback?.invoke(false)
                    return@requestStoragePermission
                }
                kotlin.runCatching {
                    val intent = Intent()
                    intent.component = cn
                    intent.action = Intent.ACTION_SEND
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_STREAM, UriUtils.file2Uri(file))
                    if (extras != null) {
                        intent.putExtras(extras)
                    }
                    context.startActivity(intent)
                    callback?.invoke(true)
                }.onFailure {
                    if (it is ActivityNotFoundException && name?.isNotEmpty() == true) {
                        toast("未安装$name")
                    } else {
                        toast("分享失败")
                    }
                    callback?.invoke(false)
                }
            } else {
                toast("未授予存储权限，请授予后重试")
                callback?.invoke(false)
            }
        }
    }

    private fun shareText(
        context: Context,
        text: String,
        cn: ComponentName? = null,
        name: String? = null,
        callback: ((Boolean) -> Unit)? = null,
    ) {
        kotlin.runCatching {
            val intent = Intent()
            intent.component = cn
            intent.action = Intent.ACTION_SEND
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
            intent.putExtra(Intent.EXTRA_TITLE, AppUtils.getAppName())
            intent.putExtra(Intent.EXTRA_TEXT, text)
            context.startActivity(intent)
            callback?.invoke(true)
        }.onFailure {
            if (it is ActivityNotFoundException && name?.isNotEmpty() == true) {
                toast("未安装$name")
            } else {
                toast("分享失败")
            }
            callback?.invoke(false)
        }
    }
}