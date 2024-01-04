package top.wangchenyan.common.sample

import android.view.View
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.permission.Permissioner
import top.wangchenyan.common.ui.fragment.BaseFragment
import me.wcy.router.annotation.Route
import top.wangchenyan.common.sample.databinding.FragmentPermissionBinding

/**
 * Created by wangchenyan.top on 2023/8/3.
 */
@Route("/permission")
class PermissionFragment : BaseFragment() {
    private val viewBinding by viewBindings<FragmentPermissionBinding>()

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        viewBinding.btnNotification.setOnClickListener {
            Permissioner.requestNotificationPermission(requireContext()) { granted, shouldRationale ->
                if (granted) {
                    toast("授权成功")
                } else {
                    toast("授权失败")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewBinding.btnNotification.text =
            "通知权限: ${if (Permissioner.hasNotificationPermission(requireContext())) "开" else "关"}"
    }
}