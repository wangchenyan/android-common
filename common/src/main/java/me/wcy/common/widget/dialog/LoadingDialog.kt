package me.wcy.common.widget.dialog

import android.content.Context
import android.os.Bundle
import me.wcy.common.R

/**
 * Created by wangchenyan on 2018/8/29.
 */
class LoadingDialog(context: Context) : BaseDialog(context, R.style.CommonDialog) {

    init {
        setContentView(R.layout.common_dialog_loading)
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setDimAmount(0f)
    }

    fun setMessage(message: CharSequence) {
    }
}