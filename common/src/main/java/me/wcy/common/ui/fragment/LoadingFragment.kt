package me.wcy.common.ui.fragment

import android.view.View
import androidx.annotation.CallSuper
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import me.wcy.common.loadsir.BaseEmptyCallback
import me.wcy.common.loadsir.BaseErrorCallback
import me.wcy.common.loadsir.BaseLoadingCallback
import me.wcy.common.ui.activity.LoadingActivity

/**
 * Created by wcy on 2020/12/22.
 */
abstract class LoadingFragment : LazyFragment() {
    protected var loadService: LoadService<Any>? = null

    @CallSuper
    override fun onLazyCreate() {
        super.onLazyCreate()
        if (isUseLoadSir()) {
            loadService = getLoadSir().register(getLoadSirTarget()) { onReload() }
        }
    }

    protected open fun isUseLoadSir(): Boolean {
        return false
    }

    protected open fun getLoadSir(): LoadSir {
        return LoadSir.Builder()
            .addCallback(getLoadingCallback())
            .addCallback(getEmptyCallback())
            .addCallback(getErrorCallback())
            .build()
    }

    protected open fun getLoadingCallback(): Callback {
        return BaseLoadingCallback()
    }

    protected open fun getEmptyCallback(): Callback {
        return BaseEmptyCallback()
    }

    protected open fun getErrorCallback(): Callback {
        return BaseErrorCallback()
    }

    protected open fun getLoadSirTarget(): View {
        return getRootView()
    }

    protected open fun onReload() {
    }

    protected open fun showLoadSirLoading() {
        loadService?.showCallback(BaseLoadingCallback::class.java)
    }

    protected open fun showLoadSirSuccess() {
        loadService?.showSuccess()
    }

    protected open fun showLoadSirEmpty(message: String? = null) {
        loadService?.showCallback(BaseEmptyCallback::class.java)
        if (message?.isNotEmpty() == true) {
            loadService?.setCallBack(
                BaseEmptyCallback::class.java,
                BaseEmptyCallback.MessageTransport(message)
            )
        }
    }

    protected open fun showLoadSirError(message: String? = null) {
        loadService?.showCallback(BaseErrorCallback::class.java)
        if (message?.isNotEmpty() == true) {
            loadService?.setCallBack(
                BaseErrorCallback::class.java,
                BaseErrorCallback.MessageTransport(message)
            )
        }
    }

    fun showLoading(cancelable: Boolean = true) {
        val activity = activity
        if (activity is LoadingActivity) {
            activity.showLoading(cancelable)
        }
    }

    fun showLoading(message: String, cancelable: Boolean = true) {
        val activity = activity
        if (activity is LoadingActivity) {
            activity.showLoading(message, cancelable)
        }
    }

    fun dismissLoading() {
        val activity = activity
        if (activity is LoadingActivity) {
            activity.dismissLoading()
        }
    }
}