package top.wangchenyan.common.ui.fragment

/**
 * Created by wangchenyan.top on 2022/6/27.
 */
interface BackEventInterceptor {
    fun onInterceptBackEvent(): Boolean {
        return false
    }
}