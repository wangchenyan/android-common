package top.wangchenyan.common.storage

import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import top.wangchenyan.common.ext.md5
import top.wangchenyan.common.utils.DES
import top.wangchenyan.common.utils.GsonUtils
import kotlin.reflect.KClass

/**
 * Created by wcy on 2021/2/1.
 */
open class PreferencesFile(context: Context, name: String, val encrypt: Boolean = true) :
    IPreferencesFile {
    private val sp = context.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return get(key, defValue, Boolean::class)
    }

    override fun putBoolean(key: String, value: Boolean) {
        put(key, value, Boolean::class)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return get(key, defValue, Int::class)
    }

    override fun putInt(key: String, value: Int) {
        put(key, value, Int::class)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return get(key, defValue, Long::class)
    }

    override fun putLong(key: String, value: Long) {
        put(key, value, Long::class)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return get(key, defValue, Float::class)
    }

    override fun putFloat(key: String, value: Float) {
        put(key, value, Float::class)
    }

    override fun getString(key: String, defValue: String): String {
        return get(key, defValue, String::class)
    }

    override fun putString(key: String, value: String) {
        put(key, value, String::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(key: String, defValue: T, type: KClass<T>): T {
        if (encrypt) {
            val encryptValue = sp.getString(key.md5(), null)
            if (encryptValue.isNullOrEmpty()) {
                return defValue
            }
            val decryptValue = DES.decrypt(encryptKey, encryptValue)
            return try {
                when (type) {
                    Boolean::class -> java.lang.Boolean.parseBoolean(decryptValue) as T
                    Int::class -> Integer.parseInt(decryptValue) as T
                    Long::class -> java.lang.Long.parseLong(decryptValue) as T
                    Float::class -> java.lang.Float.parseFloat(decryptValue) as T
                    String::class -> decryptValue as T
                    else -> throw IllegalArgumentException("不支持的类型")
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                defValue
            }
        } else {
            return when (type) {
                Boolean::class -> sp.getBoolean(key, defValue as Boolean) as T
                Int::class -> sp.getInt(key, defValue as Int) as T
                Long::class -> sp.getLong(key, defValue as Long) as T
                Float::class -> sp.getFloat(key, defValue as Float) as T
                String::class -> sp.getString(key, defValue as String) as T
                else -> throw IllegalArgumentException("不支持的类型")
            }
        }
    }

    fun <T : Any> put(key: String, value: T, type: KClass<T>) {
        if (encrypt) {
            val encryptValue = when (type) {
                Boolean::class, Int::class, Long::class, Float::class, String::class ->
                    DES.encrypt(encryptKey, value.toString())
                else -> throw IllegalArgumentException("不支持的类型")
            }
            sp.edit().putString(key.md5(), encryptValue).apply()
        } else {
            when (type) {
                Boolean::class -> sp.edit().putBoolean(key, value as Boolean).apply()
                Int::class -> sp.edit().putInt(key, value as Int).apply()
                Long::class -> sp.edit().putLong(key, value as Long).apply()
                Float::class -> sp.edit().putFloat(key, value as Float).apply()
                String::class -> sp.edit().putString(key, value as String).apply()
                else -> throw IllegalArgumentException("不支持的类型")
            }
        }
    }

    override fun <T> getModel(key: String, clazz: Class<T>): T? {
        val json = getString(key, "")
        if (json.isNotEmpty()) {
            return com.blankj.utilcode.util.GsonUtils.fromJson(json, clazz)
        }
        return null
    }

    override fun <T> putModel(key: String, t: T?) {
        if (t == null) {
            remove(key)
        } else {
            putString(key, com.blankj.utilcode.util.GsonUtils.toJson(t))
        }
    }

    override fun <T> getList(key: String, clazz: Class<T>): List<T>? {
        val json = getString(key, "")
        if (json.isNotEmpty()) {
            return GsonUtils.fromJsonList(json, clazz)
        }
        return null
    }

    override fun <T> putList(key: String, list: List<T>?) {
        putModel(key, list)
    }

    override fun remove(key: String) {
        val realKey = if (encrypt) key.md5() else key
        sp.edit().remove(realKey).apply()
    }

    override fun remove(keys: List<String>) {
        val realKeys = if (encrypt) keys.map { it.md5() } else keys
        val editor = sp.edit()
        realKeys.forEach { key ->
            editor.remove(key)
        }
        editor.apply()
    }

    override fun removeExcept(exceptKeys: List<String>) {
        val realExceptKeys = if (encrypt) exceptKeys.map { it.md5() } else exceptKeys
        val keys = HashSet(sp.all.keys)
        val editor = sp.edit()
        keys.forEach { key ->
            if (key !in realExceptKeys) {
                editor.remove(key)
            }
        }
        editor.apply()
    }

    override fun clear() {
        sp.edit().clear().apply()
    }

    companion object {
        private val encryptKey by lazy {
            val androidId = DeviceUtils.getAndroidID()
            val key = if (androidId.isNullOrEmpty()) AppUtils.getAppPackageName() else androidId
            key.md5().substring(0, 8)
        }
    }
}