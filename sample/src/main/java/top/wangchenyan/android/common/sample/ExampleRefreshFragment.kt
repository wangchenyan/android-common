package top.wangchenyan.android.common.sample

import top.wangchenyan.android.common.model.CommonResult
import top.wangchenyan.android.common.ui.fragment.SimpleRefreshFragment
import me.wcy.radapter3.RAdapter
import me.wcy.radapter3.RItemBinder
import me.wcy.router.annotation.Route
import top.wangchenyan.android.common.sample.databinding.ItemRefreshListBinding

/**
 * Created by wangchenyan.top on 2023/4/16.
 */
@Route("/refresh_list")
class ExampleRefreshFragment : SimpleRefreshFragment<String>() {
    private var cursor = 0

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
        if (page == 1) {
            cursor = 0
        }
        val list = mutableListOf<String>()
        for (i in cursor until cursor + 10) {
            list.add(i.toString())
        }
        cursor += 10
        return CommonResult.success(list)
    }

    class TextViewBinder : RItemBinder<ItemRefreshListBinding, String>() {
        override fun onBind(viewBinding: ItemRefreshListBinding, item: String, position: Int) {
            viewBinding.root.text = item
        }
    }
}