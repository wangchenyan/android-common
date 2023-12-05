package top.wangchenyan.android.common.ext

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by wcy on 2019/7/6.
 */

/**
 * 保留n位小数
 */
fun Double?.format(dot: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_EVEN): String {
    if (this == null) {
        return ""
    }
    val format = DecimalFormat()
    format.maximumFractionDigits = dot
    format.minimumFractionDigits = dot
    format.isGroupingUsed = false
    format.roundingMode = roundingMode
    return format.format(this).fixDecimalPoint()
}

/**
 * 四舍五入保留2位小数
 */
fun Double?.moneyString(): String {
    if (this == null) {
        return ""
    }
    val format = DecimalFormat()
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 2
    format.isGroupingUsed = false
    format.roundingMode = RoundingMode.HALF_EVEN
    return format.format(this).fixDecimalPoint()
}

/**
 * 四舍五入保留2位小数，并移除多余的0
 */
fun Double?.moneyStringFriendly(): String {
    if (this == null) {
        return ""
    }
    val money = moneyFormat()
    val decimalFormat = DecimalFormat("###################.###########")
    return decimalFormat.format(money).fixDecimalPoint()
}

/**
 * 去除多余的小数点
 */
fun Double?.moneyFormat(): Double {
    if (this == null) {
        return 0.0
    }
    return this.moneyString().toDoubleOrNull() ?: 0.0
}

/**
 * 浮点运算
 */
fun Double.add(value: Double): Double {
    val f1 = BigDecimal(this.toString())
    val f2 = BigDecimal(value.toString())
    return f1.add(f2).toDouble()
}

/**
 * 浮点运算
 */
fun Double.subtract(value: Double): Double {
    val f1 = BigDecimal(this.toString())
    val f2 = BigDecimal(value.toString())
    return f1.subtract(f2).toDouble()
}

/**
 * 浮点运算
 */
fun Double.multiply(value: Double): Double {
    val f1 = BigDecimal(this.toString())
    val f2 = BigDecimal(value.toString())
    return f1.multiply(f2).toDouble()
}

/**
 * 浮点运算
 */
fun Double.divide(value: Double): Double {
    val f1 = BigDecimal(this.toString())
    val f2 = BigDecimal(value.toString())
    return f1.divide(f2, 2, RoundingMode.HALF_EVEN).toDouble()
}

/**
 * 修复部分机型小数点是','的问题
 */
private fun String.fixDecimalPoint() = this.replace(',', '.', true)
