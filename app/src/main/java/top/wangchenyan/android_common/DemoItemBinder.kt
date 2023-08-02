package top.wangchenyan.android_common

import me.wcy.radapter3.RItemBinder
import top.wangchenyan.android_common.databinding.ItemDemoBinding

/**
 * Created by wangchenyan.top on 2023/8/2.
 */
class DemoItemBinder : RItemBinder<ItemDemoBinding, DemoItemType>() {
    override fun onBind(viewBinding: ItemDemoBinding, item: DemoItemType, position: Int) {
        viewBinding.root.text = item.first
        viewBinding.root.setOnClickListener {
            item.second.invoke()
        }
    }
}