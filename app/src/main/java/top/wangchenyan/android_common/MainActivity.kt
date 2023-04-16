package top.wangchenyan.android_common

import android.os.Bundle
import me.wcy.common.ext.viewBindings
import me.wcy.common.ui.activity.BaseActivity
import top.wangchenyan.android_common.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewBinding by viewBindings<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.root, ExampleRefreshFragment())
            .commitAllowingStateLoss()
    }
}