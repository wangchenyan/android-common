package me.wcy.common.loadsir

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.Transport
import me.wcy.common.CommonApp
import me.wcy.common.R
import me.wcy.common.ext.fixClick

/**
 * Created by wangchenyan.top on 2022/6/4.
 */
class BaseEmptyCallback(
    private val imageRes: Int = R.drawable.common_ic_page_empty,
    private val emptyText: String = CommonApp.app.getString(R.string.common_empty),
    private val buttonText: String? = null
) : Callback() {

    override fun onCreateView(): Int {
        return R.layout.common_load_sir_empty
    }

    override fun onViewCreate(context: Context?, view: View?) {
        super.onViewCreate(context, view)
        view ?: return
        val ivImage: ImageView = view.findViewById(R.id.ivImage)
        val tvEmpty: TextView = view.findViewById(R.id.tvEmpty)
        val btnAction: TextView = view.findViewById(R.id.btnAction)
        ivImage.setImageResource(imageRes)
        tvEmpty.text = emptyText
        if (buttonText?.isNotEmpty() == true) {
            btnAction.isVisible = true
            btnAction.text = buttonText
        } else {
            btnAction.isVisible = false
        }

        if (view is NestedScrollView) {
            view.fixClick()
        }
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return TextUtils.isEmpty(buttonText)
    }

    class MessageTransport(private val message: String) : Transport {
        override fun order(context: Context?, view: View?) {
            if (context == null || view == null) return
            view.findViewById<TextView>(R.id.tvEmpty)?.text = message
        }
    }
}