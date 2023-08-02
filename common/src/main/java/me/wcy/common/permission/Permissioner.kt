package me.wcy.common.permission

import android.Manifest
import android.content.Context
import androidx.annotation.MainThread
import com.qw.soul.permission.SoulPermission
import com.qw.soul.permission.bean.Permission
import com.qw.soul.permission.bean.Permissions
import com.qw.soul.permission.bean.Special
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener
import com.qw.soul.permission.callbcak.SpecialPermissionListener
import me.wcy.common.utils.AndroidVersionUtils

/**
 * Created by wcy on 2019/7/27.
 */
object Permissioner {

    fun goApplicationSettings(context: Context, onBack: (() -> Unit)? = null) {
        kotlin.runCatching {
            SoulPermission.getInstance().goApplicationSettings {
                onBack?.invoke()
            }
        }
    }

    private fun requestPermissions(
        context: Context,
        permissions: Array<String>,
        callback: ((allGranted: Boolean, grantedList: List<String>?, deniedList: List<String>?, shouldRationale: Boolean) -> Unit)?
    ) {
        SoulPermission.getInstance().checkAndRequestPermissions(
            Permissions.build(*permissions),
            object : CheckRequestPermissionsListener {
                override fun onAllPermissionOk(allPermissions: Array<Permission>) {
                    val grantedPermissions = allPermissions.map { it.permissionName }
                    callback?.invoke(true, grantedPermissions, null, false)
                }

                override fun onPermissionDenied(refusedPermissions: Array<Permission>) {
                    val deniedList = refusedPermissions.map { it.permissionName }
                    val shouldRationale = refusedPermissions.find { it.shouldRationale() } != null
                    callback?.invoke(false, null, deniedList, shouldRationale)
                }
            })
    }

    @MainThread
    fun requestStoragePermission(
        context: Context,
        callback: PermissionCallback?
    ) {
        val permissions = if (AndroidVersionUtils.isAboveOrEqual13()) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        requestPermissions(
            context,
            permissions
        ) { allGranted, grantedList, deniedList, shouldRationale ->
            callback?.invoke(allGranted, shouldRationale)
        }
    }

    @MainThread
    fun requestPhonePermission(
        context: Context,
        callback: PermissionCallback?
    ) {
        requestPermissions(
            context,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
        ) { allGranted, grantedList, deniedList, shouldRationale ->
            callback?.invoke(allGranted, shouldRationale)
        }
    }

    @MainThread
    fun requestCameraPermission(
        context: Context,
        callback: PermissionCallback?
    ) {
        requestPermissions(
            context,
            arrayOf(Manifest.permission.CAMERA),
        ) { allGranted, grantedList, deniedList, shouldRationale ->
            callback?.invoke(allGranted, shouldRationale)
        }
    }

    @MainThread
    fun requestCameraStoragePermission(
        context: Context,
        callback: PermissionCallback?
    ) {
        requestPermissions(
            context,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ),
        ) { allGranted, grantedList, deniedList, shouldRationale ->
            callback?.invoke(allGranted, shouldRationale)
        }
    }

    fun hasNotificationPermission(context: Context): Boolean {
        return SoulPermission.getInstance().checkSpecialPermission(Special.NOTIFICATION)
    }

    @MainThread
    fun requestNotificationPermission(context: Context, callback: SpecialPermissionCallback?) {
        SoulPermission.getInstance()
            .checkAndRequestPermission(Special.NOTIFICATION, object : SpecialPermissionListener {
                override fun onGranted(permission: Special?) {
                    callback?.invoke(true)
                }

                override fun onDenied(permission: Special?) {
                    callback?.invoke(false)
                }
            })
    }

    fun hasInstallPermission(context: Context): Boolean {
        return SoulPermission.getInstance().checkSpecialPermission(Special.UNKNOWN_APP_SOURCES)
    }

    @MainThread
    fun requestInstallPermission(context: Context, callback: SpecialPermissionCallback?) {
        SoulPermission.getInstance()
            .checkAndRequestPermission(
                Special.UNKNOWN_APP_SOURCES,
                object : SpecialPermissionListener {
                    override fun onGranted(permission: Special?) {
                        callback?.invoke(true)
                    }

                    override fun onDenied(permission: Special?) {
                        callback?.invoke(false)
                    }
                })
    }

    fun hasSettingPermission(context: Context): Boolean {
        return SoulPermission.getInstance().checkSpecialPermission(Special.WRITE_SETTINGS)
    }

    @MainThread
    fun requestSettingPermission(context: Context, callback: SpecialPermissionCallback?) {
        SoulPermission.getInstance()
            .checkAndRequestPermission(Special.WRITE_SETTINGS, object : SpecialPermissionListener {
                override fun onGranted(permission: Special?) {
                    callback?.invoke(true)
                }

                override fun onDenied(permission: Special?) {
                    callback?.invoke(false)
                }
            })
    }
}