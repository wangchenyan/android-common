package top.wangchenyan.android.common.sample

import android.os.Bundle
import top.wangchenyan.android.common.ext.viewBindings
import top.wangchenyan.android.common.ui.activity.BaseActivity
import top.wangchenyan.android.common.widget.decoration.MarginDividerDecoration
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import top.wangchenyan.android.common.sample.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewBinding by viewBindings<ActivityMainBinding>()
    private val adapter = RAdapter<DemoItemType>()

    private val items: List<DemoItemType> = listOf(
        Pair("RefreshList") {
            CRouter.with().url("/refresh_list").start()
        },
        Pair("ImagePicker") {
            CRouter.with().url("/image_picker").start()
        },
        Pair("Share") {
            CRouter.with().url("/share").start()
        },
        Pair("Permission") {
            CRouter.with().url("/permission").start()
        },
        Pair("Api") {
            CRouter.with().url("/api").start()
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        adapter.register(DemoItemBinder())
        viewBinding.recyclerView.addItemDecoration(MarginDividerDecoration(this))
        viewBinding.recyclerView.adapter = adapter
        adapter.refresh(items)
    }
}