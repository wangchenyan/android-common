package top.wangchenyan.android.common.ui.activity

import android.content.Intent
import me.wcy.router.RouterIntent

/**
 * Created by wcy on 2020/12/22.
 */
abstract class RouterActivity : CoreActivity() {

    override fun getIntent(): Intent {
        return RouterIntent(super.getIntent())
    }
}