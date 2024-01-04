package top.wangchenyan.common.widget.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.blankj.utilcode.util.ScreenUtils
import top.wangchenyan.common.R

/**
 * @author wcy
 * @date 2018/7/21
 */
open class BaseBottomDialog(context: Context) : BaseDialog(context, R.style.CommonDialog_Bottom) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
    }

    private fun initParams() {
        val params = window!!.attributes
        params.gravity = Gravity.BOTTOM
        params.width = ScreenUtils.getScreenWidth()
        window!!.attributes = params
    }
}