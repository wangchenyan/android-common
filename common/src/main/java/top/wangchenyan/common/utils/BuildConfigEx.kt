package top.wangchenyan.common.utils

import android.content.Context
import top.wangchenyan.common.CommonApp
import java.lang.reflect.Field

/**
 * Created by wcy on 2020/12/10.
 */
object BuildConfigEx {

    /**
     * 解决 Library 中无法获取 DEBUG 模式
     */
    val DEBUG: Boolean by lazy {
        getBuildConfigValue(CommonApp.app, "DEBUG") == true
    }

    /**
     * Gets a field from the project's BuildConfig. This is useful when, for example, flavors
     * are used at the project level to set custom fields.
     * @param context       Used to find the correct file
     * @param fieldName     The name of the field-to-access
     * @return              The value of the field, or `null` if the field is not found.
     */
    fun getBuildConfigValue(context: Context, fieldName: String): Any? {
        try {
            val clazz = Class.forName("${context.packageName}.BuildConfig")
            val field: Field = clazz.getField(fieldName)
            return field.get(null)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }
}