package top.wangchenyan.common.widget.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.blankj.utilcode.util.ScreenUtils
import top.wangchenyan.common.R

/**
 * Created by wcy on 2019/7/14.
 */
open class BaseCenterDialog(
    context: Context,
    private val width: Int? = null
) : BaseDialog(context, R.style.CommonDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
    }

    private fun initParams() {
        val params = window!!.attributes
        params.width = width ?: (ScreenUtils.getScreenWidth() * 0.85f).toInt()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
    }
}