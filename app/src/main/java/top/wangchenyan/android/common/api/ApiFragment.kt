package top.wangchenyan.android.common.api

import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.wcy.common.ext.viewBindings
import me.wcy.common.net.apiCall
import me.wcy.common.ui.fragment.BaseFragment
import me.wcy.common.utils.GsonUtils
import me.wcy.router.annotation.Route
import top.wangchenyan.android.common.databinding.FragmentApiBinding

/**
 * Created by wangchenyan.top on 2023/12/5.
 */
@Route("/api")
class ApiFragment : BaseFragment() {
    private val viewBinding by viewBindings<FragmentApiBinding>()

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        viewBinding.btnRequest.setOnClickListener {
            lifecycleScope.launch {
                viewBinding.tvLog.append("开始请求\n")
                val res = apiCall { WanAndroidApi.get().getHomeArticleList(0) }
                if (res.isSuccessWithData()) {
                    viewBinding.tvLog.append("请求成功\n")
                    viewBinding.tvLog.append(GsonUtils.toPrettyJson(res.getDataOrThrow()))
                } else {
                    viewBinding.tvLog.append("请求出错, code: ${res.code}, msg: ${res.msg}\n")
                }
            }
        }
    }
}