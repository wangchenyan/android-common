package top.wangchenyan.android.common.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorRes
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.immersionbar.ImmersionBar
import top.wangchenyan.android.common.R
import top.wangchenyan.android.common.utils.StatusBarUtils
import top.wangchenyan.android.common.widget.TitleLayout

abstract class BaseActivity : LoadingActivity() {
    private var titleLayout: TitleLayout? = null
    private var isStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, javaClass.simpleName + ": onCreate")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (StatusBarUtils.isSupportStatusBarTransparent()) {
            ImmersionBar.with(this)
                .navigationBarColor(getNavigationBarColor())
                .navigationBarDarkIcon(true)
                .keyboardEnable(true)
                .init()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isStart.not()) {
            onFirstStart()
            isStart = true
        }
    }

    protected open fun onFirstStart() {
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    open fun getTitleLayout(): TitleLayout? {
        if (titleLayout == null) {
            titleLayout = findViewById(R.id.common_title_layout)
        }
        return titleLayout
    }

    override fun setTitle(title: CharSequence?) {
        getTitleLayout()?.setTitleText(title)
    }

    @ColorRes
    protected open fun getNavigationBarColor(): Int {
        return R.color.common_background_color
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, javaClass.simpleName + ": onDestroy")
    }

    override fun onBackPressed() {
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this)
        }
        super.onBackPressed()
    }

    protected fun setResultAndFinish(data: Intent? = null) {
        if (data == null) {
            setResult(Activity.RESULT_OK)
        } else {
            setResult(Activity.RESULT_OK, data)
        }
        finish()
    }
}