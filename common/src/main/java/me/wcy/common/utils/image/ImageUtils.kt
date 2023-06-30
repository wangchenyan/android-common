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
}