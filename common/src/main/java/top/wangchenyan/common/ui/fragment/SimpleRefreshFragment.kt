package top.wangchenyan.common.ui.fragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import top.wangchenyan.common.databinding.CommonFragmentRefreshListBinding
import top.wangchenyan.common.ext.viewBindings

/**
 * Created by wangchenyan.top on 2022/9/24.
 */
abstract class SimpleRefreshFragment<T> : BaseRefreshFragment<T>() {
    protected val viewBinding by viewBindings<CommonFragmentRefreshListBinding>()

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun getRefreshLayout(): SmartRefreshLayout {
        return viewBinding.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return viewBinding.recyclerView
    }

    protected abstract fun isShowTitle(): Boolean

    override fun onLazyCreate() {
        super.onLazyCreate()
        if (isShowTitle()) {
            viewBinding.titleLayoutStub.inflate()
        }
    }
}