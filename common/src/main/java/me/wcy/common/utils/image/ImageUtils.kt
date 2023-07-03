package me.wcy.common.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.FileUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import me.wcy.common.CommonApp
import me.wcy.common.const.FilePath
import me.wcy.common.model.CommonResult
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
        Glide.with(CommonApp.app)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(CommonResult.success(resource))
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback(CommonResult.fail())
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
        url: String,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        callback: (CommonResult<Unit>) -> Unit
    ) {
        loadBitmap(url) { res ->
            if (res.isSuccessWithData()) {
                com.blankj.utilcode.util.ImageUtils.save2Album(
                    res.getDataOrThrow(),
                    format
                )?.also {
                    callback(CommonResult.success(Unit))
                } ?: kotlin.run {
                    callback(CommonResult.fail(msg = "保存失败"))
                }
            } else {
                callback(CommonResult.fail(res.code, res.msg))
            }
        }
    }

    fun save2Album(
        list: List<String>,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        callback: (CommonResult<Unit>) -> Unit
    ) {
        var saveCount = 0
        var successCount = 0
        list.forEach { url ->
            save2Album(url, format) { res ->
                if (res.isSuccess()) {
                    successCount++
                }
                saveCount++
                if (saveCount == list.size) {
                    if (successCount == saveCount) {
                        callback(CommonResult.success(Unit))
                    } else if (successCount == 0) {
                        callback(CommonResult.fail(msg = "保存失败"))
                    } else {
                        callback(CommonResult.fail(msg = "保存成功${successCount}张图片"))
                    }
                }
            }
        }
    }
}