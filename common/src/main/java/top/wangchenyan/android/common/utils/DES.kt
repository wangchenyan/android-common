package top.wangchenyan.android.common.utils

import android.util.Log
import com.blankj.utilcode.util.EncryptUtils

object DES {
    private const val TAG = "DES"
    private val IV_PARAMETER = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    private const val CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding"

    fun encrypt(key: String, data: String): String {
        return try {
            val bytes = EncryptUtils.encryptDES2Base64(
                data.toByteArray(),
                key.toByteArray(),
                CIPHER_ALGORITHM,
                IV_PARAMETER
            )
            String(bytes)
        } catch (t: Throwable) {
            Log.e(TAG, "encrypt error, key: $key, data: $data", t)
            ""
        }
    }

    fun decrypt(key: String, data: String): String {
        return try {
            val bytes = EncryptUtils.decryptBase64DES(
                data.toByteArray(),
                key.toByteArray(),
                CIPHER_ALGORITHM,
                IV_PARAMETER
            )
            String(bytes)
        } catch (t: Throwable) {
            Log.e(TAG, "decrypt error, key: $key, data: $data", t)
            ""
        }
    }
}