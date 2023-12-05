package top.wangchenyan.android.common.widget.dialog

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.blankj.utilcode.util.ActivityUtils

/**
 * Created by wangchenyan.top on 2022/10/5.
 */
class BaseProgressDialog(context: Context?) : ProgressDialog(context) {

    override fun show() {
        if (ActivityUtils.isActivityAlive(context)) {
            kotlin.runCatching {
                super.show()
            }
        } else {
            Log.w(TAG, "dialog can not show with context $context")
        }
    }

    override fun dismiss() {
        kotlin.runCatching {
            super.dismiss()
        }
    }

    companion object {
        private const val TAG = "BaseProgressDialog"
    }
}