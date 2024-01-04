package top.wangchenyan.common.ui.activity

import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import top.wangchenyan.common.R
import top.wangchenyan.common.loadsir.DefaultEmptyCallback
import top.wangchenyan.common.loadsir.DefaultErrorCallback
import top.wangchenyan.common.loadsir.DefaultLoadingCallback
import top.wangchenyan.common.widget.dialog.LoadingDialog

/**
 * Created by wcy on 2020/12/22.
 */
abstract class LoadingActivity : RouterActivity() {
    protected var loadService: LoadService<Any>? = null
    private val progressDialog: LoadingDialog by lazy {
        val dialog = LoadingDialog(this)
        dialog.setCancelable(true)
        return@lazy dialog
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initLoadSir()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initLoadSir()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        initLoadSir()
    }

    private fun initLoadSir() {
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
        return DefaultLoadingCallback()
    }

    protected open fun getEmptyCallback(): Callback {
        return DefaultEmptyCallback()
    }

    protected open fun getErrorCallback(): Callback {
        return DefaultErrorCallback()
    }

    protected open fun getLoadSirTarget(): Any {
        return this
    }

    protected open fun onReload() {
    }

    protected open fun showLoadSirLoading() {
        loadService?.showCallback(DefaultLoadingCallback::class.java)
    }

    protected open fun showLoadSirSuccess() {
        loadService?.showSuccess()
    }

    protected open fun showLoadSirEmpty(message: String? = null) {
        loadService?.showCallback(DefaultEmptyCallback::class.java)
        if (message?.isNotEmpty() == true) {
            loadService?.setCallBack(
                DefaultEmptyCallback::class.java,
                DefaultEmptyCallback.MessageTransport(message)
            )
        }
    }

    protected open fun showLoadSirError(message: String? = null) {
        loadService?.showCallback(DefaultErrorCallback::class.java)
        if (message?.isNotEmpty() == true) {
            loadService?.setCallBack(
                DefaultErrorCallback::class.java,
                DefaultErrorCallback.MessageTransport(message)
            )
        }
    }

    fun showLoading(cancelable: Boolean = true) {
        showLoading(getString(R.string.common_loading))
    }

    fun showLoading(message: String, cancelable: Boolean = true) {
        if (!progressDialog.isShowing) {
            progressDialog.setMessage(message)
            progressDialog.setCancelable(cancelable)
            progressDialog.show()
        }
    }

    fun dismissLoading() {
        if (progressDialog.isShowing) {
            progressDialog.cancel()
        }
    }
}