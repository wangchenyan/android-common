package me.wcy.common.utils.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.UriUtils
import me.wcy.common.const.FilePath
import me.wcy.common.ext.showBottomItemsDialog
import me.wcy.common.ext.toast
import me.wcy.common.model.CommonResult
import me.wcy.common.permission.Permissioner
import me.wcy.common.utils.AndroidVersionUtils
import me.wcy.router.CRouter
import java.io.File

/**
 * Created by wangchenyan on 2018/9/18.
 */
object ImagePicker {
    private const val PORTRAIT_LENGTH = 320

    fun showSelectDialog(
        context: Context,
        crop: Boolean = false,
        callback: (CommonResult<String>) -> Unit
    ) {
        context.showBottomItemsDialog(listOf("相册", "拍照")) { dialog, which ->
            if (which == 0) {
                startAlbum(context, crop, callback)
            } else if (which == 1) {
                startCamera(context, crop, callback)
            }
        }
    }

    fun startCamera(
        context: Context,
        crop: Boolean,
        callback: (CommonResult<String>) -> Unit
    ) {
        val captureFile = File(FilePath.getCacheImageFilePath())
        FileUtils.createOrExistsFile(captureFile)
        val uri = UriUtils.file2Uri(captureFile)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        CRouter.with(context)
            .intent(intent)
            .startForResult {
                if (it.isSuccess()) {
                    if (crop) {
                        startCorp(context, uri, callback)
                    } else {
                        ImageUtils.compressImage(context, captureFile) { f ->
                            handleResult(f, callback)
                        }
                    }
                }
            }
    }

    fun startAlbum(
        context: Context,
        crop: Boolean,
        callback: (CommonResult<String>) -> Unit
    ) {
        val start = {
            val action = if (AndroidVersionUtils.isAboveOrEqual13()) {
                MediaStore.ACTION_PICK_IMAGES
            } else {
                Intent.ACTION_PICK
            }
            val intent = Intent(action)
            intent.type = "image/*"
            CRouter.with(context)
                .intent(intent)
                .startForResult {
                    if (it.isSuccess() && it.data?.data != null) {
                        if (crop) {
                            startCorp(context, it.data?.data!!, callback)
                        } else {
                            val file = UriUtils.uri2File(it.data?.data!!)
                            ImageUtils.compressImage(context, file) { f ->
                                handleResult(f, callback)
                            }
                        }
                    }
                }
        }
        Permissioner.requestStoragePermission(context) { granted, _ ->
            if (granted) {
                start()
            } else {
                toast("授权失败，无法打开相册")
            }
        }
    }

    private fun startCorp(
        context: Context,
        uri: Uri,
        callback: (CommonResult<String>) -> Unit
    ) {
        // TODO Android 12 无法剪裁
        if (AndroidVersionUtils.isAboveOrEqual12()) {
            ImageUtils.compressImage(context, UriUtils.uri2File(uri)) { f ->
                handleResult(f, callback)
            }
            return
        }
        val path = FilePath.getCacheImageFilePath()
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", PORTRAIT_LENGTH)
        intent.putExtra("outputY", PORTRAIT_LENGTH)
        intent.putExtra("scale", true)
        intent.putExtra("return-data", false)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        if (AndroidVersionUtils.isAboveOrEqual11().not()) {
            // Android 11 以下支持指定路径，Android 11 及以上需要从 Result 中获取图片路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(path)))
        }
        CRouter.with(context)
            .intent(intent)
            .startForResult {
                if (it.isSuccess()) {
                    if (AndroidVersionUtils.isAboveOrEqual11().not()) {
                        // Android 11 以下直接使用指定路径
                        handleResult(File(path), callback)
                    } else if (it.data?.data != null) {
                        // Android 11 及以上从 data 中获取文件 uri
                        val file = UriUtils.uri2File(it.data?.data)
                        handleResult(file, callback)
                    } else {
                        callback(CommonResult.fail())
                    }
                }
            }
    }

    private fun handleResult(
        file: File,
        callback: (CommonResult<String>) -> Unit
    ) {
        if (file.exists().not()) {
            callback(CommonResult.fail(msg = "file not exist"))
            return
        }
        callback.invoke(CommonResult.success(file.path))
    }
}