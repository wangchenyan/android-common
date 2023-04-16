package me.wcy.common.ui.fragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import me.wcy.common.databinding.CommonFragmentRefreshListBinding
import me.wcy.common.ext.viewBindings

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