package me.wcy.common.ui.fragment

import android.app.Activity
import android.content.Intent
import androidx.annotation.ColorRes

/**
 * Created by wcy on 2020/10/14.
 */
abstract class BaseFragment : LoadingFragment(), BackEventInterceptor {

    override fun onResume() {
        super.onResume()
        getTitleLayout()?.updateTextStyle()
    }

    @ColorRes
    open fun getNavigationBarColor(): Int = 0

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