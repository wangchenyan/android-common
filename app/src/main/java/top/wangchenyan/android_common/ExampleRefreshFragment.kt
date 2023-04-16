package top.wangchenyan.android_common

import me.wcy.common.model.CommonResult
import me.wcy.common.ui.fragment.SimpleRefreshFragment
import me.wcy.radapter3.RAdapter
import me.wcy.radapter3.RItemBinder
import top.wangchenyan.android_common.databinding.ItemSimpleBinding

/**
 * Created by wangchenyan.top on 2023/4/16.
 */
class ExampleRefreshFragment : SimpleRefreshFragment<String>() {

    override fun initAdapter(adapter: RAdapter<String>) {
        adapter.register(TextViewBinder())
    }

    override fun isShowTitle(): Boolean {
        return true
    }

    override fun onLazyCreate() {
        super.onLazyCreate()
        getTitleLayout()?.setTitleText("ExampleRefreshFragment")
    }

    override suspend fun getData(page: Int): CommonResult<List<String>> {
        val list = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        return CommonResult.success(list)
    }

    class TextViewBinder : RItemBinder<ItemSimpleBinding, String>() {
        override fun onBind(viewBinding: ItemSimpleBinding, item: String, position: Int) {
            viewBinding.root.text = item
        }
    }
}