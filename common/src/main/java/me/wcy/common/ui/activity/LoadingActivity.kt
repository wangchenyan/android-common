package me.wcy.common.ui.activity

import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import me.wcy.common.loadsir.BaseEmptyCallback
import me.wcy.common.loadsir.BaseErrorCallback
import me.wcy.common.loadsir.BaseLoadingCallback
import me.wcy.common.widget.dialog.LoadingDialog
import me.wcy.common.ui.activity.RouterActivity

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
        if (useLoadSir()) {
            loadService = getLoadSir().register(getLoadSirTarget()) { onReload() }
        }
    }

    protected open fun useLoadSir(): Boolean {
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

    protected open fun getLoadSirTarget(): Any {
        return this
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
        showLoading("加载中…")
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