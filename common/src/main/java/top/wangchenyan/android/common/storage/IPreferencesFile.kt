package top.wangchenyan.android.common.storage

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by wcy on 2021/2/1.
 */
interface IPreferencesFile {

    fun remove(key: String)

    fun remove(keys: List<String>)

    fun removeExcept(exceptKeys: List<String>)

    fun clear()

    fun getBoolean(key: String, defValue: Boolean = false): Boolean

    fun putBoolean(key: String, value: Boolean)

    fun getInt(key: String, defValue: Int = 0): Int

    fun putInt(key: String, value: Int)

    fun getLong(key: String, defValue: Long = 0): Long

    fun putLong(key: String, value: Long)

    fun getFloat(key: String, defValue: Float = 0f): Float

    fun putFloat(key: String, value: Float)

    fun getString(key: String, defValue: String = ""): String

    fun putString(key: String, value: String)

    fun <T> getModel(key: String, clazz: Class<T>): T?

    fun <T> putModel(key: String, t: T?)

    fun <T> getList(key: String, clazz: Class<T>): List<T>?

    fun <T> putList(key: String, list: List<T>?)

    class BooleanProperty(private val key: String, private val defValue: Boolean = false) :
        ReadWriteProperty<IPreferencesFile, Boolean> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): Boolean {
            return thisRef.getBoolean(key, defValue)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: Boolean) {
            thisRef.putBoolean(key, value)
        }
    }

    class IntProperty(private val key: String, private val defValue: Int = 0) :
        ReadWriteProperty<IPreferencesFile, Int> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): Int {
            return thisRef.getInt(key, defValue)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: Int) {
            thisRef.putInt(key, value)
        }
    }

    class LongProperty(private val key: String, private val defValue: Long = 0L) :
        ReadWriteProperty<IPreferencesFile, Long> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): Long {
            return thisRef.getLong(key, defValue)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: Long) {
            thisRef.putLong(key, value)
        }
    }

    class FloatProperty(private val key: String, private val defValue: Float = 0F) :
        ReadWriteProperty<IPreferencesFile, Float> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): Float {
            return thisRef.getFloat(key, defValue)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: Float) {
            thisRef.putFloat(key, value)
        }
    }

    class StringProperty(private val key: String, private val defValue: String = "") :
        ReadWriteProperty<IPreferencesFile, String> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): String {
            return thisRef.getString(key, defValue)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: String) {
            thisRef.putString(key, value)
        }
    }

    class ObjectProperty<T>(private val key: String, private val clazz: Class<T>) :
        ReadWriteProperty<IPreferencesFile, T?> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): T? {
            return thisRef.getModel(key, clazz)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: T?) {
            thisRef.putModel(key, value)
        }
    }

    class ListProperty<T>(private val key: String, private val clazz: Class<T>) :
        ReadWriteProperty<IPreferencesFile, List<T>?> {

        override fun getValue(thisRef: IPreferencesFile, property: KProperty<*>): List<T>? {
            return thisRef.getList(key, clazz)
        }

        override fun setValue(thisRef: IPreferencesFile, property: KProperty<*>, value: List<T>?) {
            thisRef.putList(key, value)
        }
    }
}