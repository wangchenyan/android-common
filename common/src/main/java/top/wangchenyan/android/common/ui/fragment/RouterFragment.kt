package top.wangchenyan.android.common.ui.fragment

import android.content.Intent
import android.os.Bundle
import me.wcy.router.RouterIntent

/**
 * Created by wangchenyan.top on 2022/10/31.
 */
abstract class RouterFragment : BasicFragment() {

    protected fun getRouteArguments(): Intent {
        return RouterIntent(Intent().also {
            it.putExtras(arguments ?: Bundle())
        })
    }
}