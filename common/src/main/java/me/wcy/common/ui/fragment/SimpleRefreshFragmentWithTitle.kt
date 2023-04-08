package me.wcy.common.ui.fragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import me.wcy.common.databinding.CommonFragmentRefreshListWithTitleBinding
import me.wcy.common.ext.viewBindings

/**
 * Created by wangchenyan.top on 2022/9/24.
 */
abstract class SimpleRefreshFragmentWithTitle<T> : BaseRefreshFragment<T>() {
    protected val viewBinding by viewBindings<CommonFragmentRefreshListWithTitleBinding>()

    final override fun getRootView(): View {
        return viewBinding.root
    }

    final override fun getRefreshLayout(): SmartRefreshLayout {
        return viewBinding.refreshLayout
    }

    final override fun getRecyclerView(): RecyclerView {
        return viewBinding.recyclerView
    }

    protected abstract fun getTitle(): CharSequence

    override fun onLazyCreate() {
        super.onLazyCreate()
        getTitleLayout()?.setTitleText(getTitle())
    }
}