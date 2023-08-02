package top.wangchenyan.android_common

import android.os.Bundle
import me.wcy.common.ext.viewBindings
import me.wcy.common.ui.activity.BaseActivity
import me.wcy.router.CRouter
import top.wangchenyan.android_common.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewBinding by viewBindings<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.btnRefreshList.setOnClickListener {
            CRouter.with().url("/refresh_list").start()
        }
        viewBinding.btnImagePicker.setOnClickListener {
            CRouter.with().url("/image_picker").start()
        }
        viewBinding.btnShare.setOnClickListener {
            CRouter.with().url("/share").start()
        }
    }
}