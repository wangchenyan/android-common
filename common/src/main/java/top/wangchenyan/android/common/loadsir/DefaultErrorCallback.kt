package top.wangchenyan.android.common.loadsir

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.Transport
import top.wangchenyan.android.common.CommonApp
import top.wangchenyan.android.common.R
import top.wangchenyan.android.common.ext.fixClick

/**
 * Created by wcy on 2021/1/9.
 */
class DefaultErrorCallback(
    val errorText: String = CommonApp.app.getString(R.string.common_empty),
    private val showRetry: Boolean = false
) : Callback() {
    class MessageTransport(private val message: String) : Transport {
        override fun order(context: Context?, view: View?) {
            if (context == null || view == null) return
            view.findViewById<TextView>(R.id.tvError)?.text = message
        }
    }

    override fun onCreateView(): Int {
        return R.layout.common_load_sir_error
    }

    override fun onViewCreate(context: Context?, view: View?) {
        super.onViewCreate(context, view)
        view ?: return
        val tvError: TextView = view.findViewById(R.id.tvError)
        val btnRetry: TextView = view.findViewById(R.id.btnRetry)
        tvError.text = errorText
        btnRetry.isVisible = showRetry

        if (view is NestedScrollView) {
            view.fixClick()
        }
    }
}