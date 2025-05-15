package top.wangchenyan.common.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import com.blankj.utilcode.util.KeyboardUtils
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.R
import top.wangchenyan.common.ext.getColorEx
import top.wangchenyan.common.insets.WindowInsetsManager
import top.wangchenyan.common.insets.WindowInsetsManager.Companion.obtainWindowInsetsManager
import top.wangchenyan.common.widget.TitleLayout

abstract class BaseActivity : LoadingActivity() {
    private var titleLayout: TitleLayout? = null
    private var isStart = false
    private var windowInsetsManager: WindowInsetsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        configWindowInsets {
            fillNavBar = true
            fillDisplayCutout = false
            navBarColor = getColorEx(R.color.common_background_color)
            displayCutoutColor = getColorEx(R.color.common_background_color)
            navBarButtonDarkStyle = CommonApp.config.isDarkMode().not()
        }

        Log.d(TAG, javaClass.simpleName + ": onCreate")
    }

    fun configWindowInsets(block: WindowInsetsManager.() -> Unit) {
        val windowInsetsManager = this.windowInsetsManager ?: obtainWindowInsetsManager().also {
            this.windowInsetsManager = it
        }
        block(windowInsetsManager)
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