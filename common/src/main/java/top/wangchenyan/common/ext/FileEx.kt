package top.wangchenyan.common.ext

import com.blankj.utilcode.util.ConvertUtils

/**
 * Created by wangchenyan.top on 2022/7/25.
 */

fun Long?.fileSize(): String {
    if (this == null) {
        return ""
    }
    return ConvertUtils.byte2FitMemorySize(this, 1)
}