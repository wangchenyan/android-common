package top.wangchenyan.common.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.FileUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.R
import top.wangchenyan.common.const.FilePath
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.permission.Permissioner
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by wangchenyan.top on 2022/10/18.
 */
object ImageUtils {

    fun loadBitmap(url: Any, callback: (CommonResult<Bitmap>) -> Unit) {
        var mutableCallback: ((CommonResult<Bitmap>) -> Unit)? = callback
        Glide.with(CommonApp.app)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    mutableCallback?.invoke(CommonResult.success(resource))
                    mutableCallback = null
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    mutableCallback?.invoke(CommonResult.fail())
                    mutableCallback = null
                }
            })
    }

    suspend fun loadBitmap(url: Any): CommonResult<Bitmap> {
        return suspendCoroutine { continuation ->
            loadBitmap(url) { res ->
                continuation.resume(res)
            }
        }
    }

    fun compressImage(
        context: Context,
        srcFile: File,
        destFile: File = File(FilePath.getCacheImageFilePath()),
        callback: (File) -> Unit
    ) {
        Luban.with(context)
            .load(srcFile)
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                }

                override fun onSuccess(compressedFile: File?) {
                    if (compressedFile?.exists() == true) {
                        FileUtils.move(compressedFile, destFile)
                        callback(destFile)
                    } else {
                        onError(null)
                    }
                }

                override fun onError(e: Throwable?) {
                    callback(srcFile)
                }
            })
            .launch()
    }

    suspend fun compressImage(
        context: Context,
        srcFile: File,
        destFile: File = File(FilePath.getCacheImageFilePath())
    ): File {
        return suspendCoroutine { continuation ->
            compressImage(context, srcFile, destFile) { file ->
                continuation.resume(file)
            }
        }
    }

    fun save2Album(
        context: Context,
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        callback: (CommonResult<Unit>) -> Unit
    ) {
        Permissioner.requestStoragePermission(context) { granted, _ ->
            if (granted) {
                com.blankj.utilcode.util.ImageUtils.save2Album(bitmap, format)
                    ?.also {
                        callback(CommonResult.success(Unit))
                    }
                    ?: kotlin.run {
                        callback(CommonResult.fail(msg = context.getString(R.string.common_save_fail)))
                    }
            } else {
                callback(CommonResult.fail(msg = context.getString(R.string.common_not_grant_storage_permission)))
            }
        }
    }

    fun save2Album(
        context: Context,
        url: String,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        callback: (CommonResult<Unit>) -> Unit
    ) {
        Permissioner.requestStoragePermission(context) { granted, _ ->
            if (granted) {
                loadBitmap(url) { res ->
                    if (res.isSuccessWithData()) {
                        save2Album(context, res.getDataOrThrow(), format, callback)
                    } else {
                        callback(CommonResult.fail(res.code, res.msg))
                    }
                }
            } else {
                callback(CommonResult.fail(msg = context.getString(R.string.common_not_grant_storage_permission)))
            }
        }
    }

    fun save2Album(
        context: Context,
        list: List<String>,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        callback: (CommonResult<Unit>) -> Unit
    ) {
        Permissioner.requestStoragePermission(context) { granted, _ ->
            if (granted) {
                var saveCount = 0
                var successCount = 0
                list.forEach { url ->
                    save2Album(context, url, format) { res ->
                        if (res.isSuccess()) {
                            successCount++
                        }
                        saveCount++
                        if (saveCount == list.size) {
                            if (successCount == saveCount) {
                                callback(CommonResult.success(Unit))
                            } else if (successCount == 0) {
                                callback(CommonResult.fail(msg = context.getString(R.string.common_save_fail)))
                            } else {
                                callback(CommonResult.fail(msg = context.getString(R.string.common_save_fail_partially)))
                            }
                        }
                    }
                }
            } else {
                callback(CommonResult.fail(msg = context.getString(R.string.common_not_grant_storage_permission)))
            }
        }
    }
}
