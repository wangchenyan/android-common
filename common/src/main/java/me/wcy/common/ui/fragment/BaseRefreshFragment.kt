package me.wcy.common.ui.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.wcy.common.R
import me.wcy.common.model.CommonResult
import me.wcy.radapter3.RAdapter

/**
 * Created by wangchenyan.top on 2022/9/24.
 */
abstract class BaseRefreshFragment<T> : BaseFragment() {
    protected val adapter = RAdapter<T>()
    private var refreshJob: Job? = null
    private var loadMoreJob: Job? = null
    private var page = 1
    private var isSuccess = false

    override fun isUseLoadSir(): Boolean {
        return true
    }

    override fun getLoadSirTarget(): View {
        return getRefreshLayout()
    }

    override fun onReload() {
        super.onReload()
        autoRefresh()
    }

    override fun onLazyCreate() {
        super.onLazyCreate()
        showLoadSirLoading()
        initAdapter(adapter)
        getRecyclerView().apply {
            initRecyclerView(this)
            layoutManager = this@BaseRefreshFragment.getLayoutManager()
            adapter = this@BaseRefreshFragment.adapter
        }
        getRefreshLayout().apply {
            setEnableRefresh(isRefreshEnabled())
            setEnableLoadMore(isLoadMoreEnabled())
            setRefreshHeader(MaterialHeader(requireContext()).apply {
                setColorSchemeResources(R.color.common_theme_color)
            })
            setRefreshFooter(ClassicsFooter(requireContext()))
            setOnRefreshListener {
                onRefresh()
            }
            setOnLoadMoreListener {
                onLoadMore()
            }
        }
        autoRefresh()
    }

    protected fun autoRefresh(withAnimation: Boolean = true) {
        if (isRefreshEnabled() && withAnimation) {
            getRefreshLayout().autoRefreshAnimationOnly()
        }
        onRefresh()
    }

    private fun onRefresh() {
        refreshJob?.cancel()
        loadMoreJob?.cancel()
        refreshJob = lifecycleScope.launch {
            if (isSuccess.not()) {
                showLoadSirLoading()
            }
            firstPage()
            getData(page).let {
                getRefreshLayout().finishRefresh()
                if (it.isSuccess()) {
                    val data = it.data
                    if (data?.isNotEmpty() == true) {
                        isSuccess = true
                        showLoadSirSuccess()
                        adapter.refresh(data)
                        getRefreshLayout().resetNoMoreData()
                    } else {
                        isSuccess = false
                        showLoadSirEmpty()
                    }
                } else {
                    isSuccess = false
                    showLoadSirError(it.msg)
                }
            }
        }
    }

    private fun onLoadMore() {
        refreshJob?.cancel()
        loadMoreJob?.cancel()
        loadMoreJob = lifecycleScope.launch {
            getData(page + 1).let {
                if (it.isSuccess()) {
                    nextPage()
                    val data = it.data
                    if (data?.isNotEmpty() == true) {
                        adapter.addAll(data)
                        getRefreshLayout().finishLoadMore()
                    } else {
                        getRefreshLayout().finishLoadMoreWithNoMoreData()
                    }
                } else {
                    getRefreshLayout().finishLoadMore(false)
                }
            }
        }
    }

    abstract override fun getRootView(): View

    abstract fun getRefreshLayout(): SmartRefreshLayout

    abstract fun getRecyclerView(): RecyclerView

    protected open fun isRefreshEnabled(): Boolean = true

    protected open fun isLoadMoreEnabled(): Boolean = true

    abstract fun initAdapter(adapter: RAdapter<T>)

    abstract suspend fun getData(page: Int): CommonResult<List<T>>

    protected open fun getLayoutManager(): LayoutManager {
        return LinearLayoutManager(requireContext())
    }

    protected open fun initRecyclerView(recyclerView: RecyclerView) {
    }

    private fun firstPage() {
        page = 1
    }

    private fun nextPage() {
        page++
    }
}