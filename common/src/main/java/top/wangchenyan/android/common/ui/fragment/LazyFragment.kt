package top.wangchenyan.android.common.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import com.blankj.utilcode.util.AppUtils
import top.wangchenyan.android.common.R
import top.wangchenyan.android.common.widget.TitleLayout
import top.wangchenyan.android.common.widget.pager.IPagerFragment

abstract class LazyFragment : RouterFragment(), IPagerFragment {
    private var titleLayout: TitleLayout? = null
    private var isLazyCreated: Boolean = false

    protected abstract fun getRootView(): View

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val rootView = FrameLayout(requireContext())
        val rootLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        rootView.layoutParams = rootLayoutParams
        return rootView
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "${javaClass.simpleName} -> onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLazy().not()) {
            lazyCreate()
        }
    }

    override fun onResume() {
        if (isLazyCreated.not()) {
            lazyCreate()
        }
        super.onResume()
        Log.d(TAG, "${javaClass.simpleName} -> onResume")
    }

    private fun lazyCreate() {
        isLazyCreated = true
        val rootView = view as ViewGroup?
        rootView?.addView(getRootView())
        onLazyCreate()
        onPostCreate()
    }

    @CallSuper
    protected open fun onLazyCreate() {
        Log.d(TAG, "${javaClass.simpleName} -> onLazyCreate")
    }

    @CallSuper
    protected open fun onPostCreate() {
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${javaClass.simpleName} -> onPause")
    }

    /**
     * Please override [onLazyDestroy]
     */
    final override fun onDestroy() {
        super.onDestroy()
        if (isLazyCreated) {
            onLazyDestroy()
        }
    }

    protected open fun isLazy(): Boolean {
        return true
    }

    protected fun isLazyCreated(): Boolean {
        return isLazyCreated
    }

    open fun getTitleLayout(): TitleLayout? {
        if (titleLayout == null) {
            titleLayout = getRootView().findViewById(R.id.common_title_layout)
        }
        return titleLayout
    }

    override fun getTabText(): CharSequence {
        return AppUtils.getAppName()
    }

    protected open fun onLazyDestroy() {
        Log.d(TAG, "${javaClass.simpleName} -> onLazyDestroy")
    }
}
