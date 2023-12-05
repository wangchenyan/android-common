package top.wangchenyan.android.common.ui.activity

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by wcy on 2021/1/10.
 */
abstract class CoreActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Common-Activity"
    }

    override fun onBackPressed() {
        try {
            super.onBackPressed()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}