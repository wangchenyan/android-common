package top.wangchenyan.common.ui.fragment

import android.app.Activity
import android.content.Intent
import top.wangchenyan.common.insets.WindowInsetsManager
import top.wangchenyan.common.ui.activity.BaseActivity

/**
 * Created by wcy on 2020/10/14.
 */
abstract class BaseFragment : LoadingFragment(), BackEventInterceptor {

    protected fun configWindowInsets(block: WindowInsetsManager.() -> Unit) {
        val activity = activity
        if (activity is BaseActivity) {
            activity.configWindowInsets(block)
        }
    }

    override fun onResume() {
        super.onResume()
        getTitleLayout()?.applyTextStyle()
    }

    protected fun setResult(data: Intent? = null) {
        if (data == null) {
            activity?.setResult(Activity.RESULT_OK)
        } else {
            activity?.setResult(Activity.RESULT_OK, data)
        }
    }

    protected fun setResultAndFinish(data: Intent? = null) {
        setResult(data)
        activity?.finish()
    }

    protected fun finish() {
        activity?.finish()
    }
}