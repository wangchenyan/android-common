package top.wangchenyan.android_common

import android.graphics.BitmapFactory
import android.view.View
import me.wcy.common.ext.showBottomItemsDialog
import me.wcy.common.ext.viewBindings
import me.wcy.common.ui.fragment.BaseFragment
import me.wcy.common.utils.ShareUtils
import me.wcy.common.utils.image.ImagePicker
import me.wcy.router.annotation.Route
import top.wangchenyan.android_common.databinding.FragmentShareBinding

/**
 * Created by wangchenyan.top on 2023/8/2.
 */
@Route("/share")
class ShareFragment : BaseFragment() {
    private val viewBinding by viewBindings<FragmentShareBinding>()

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()
        viewBinding.btnShareText.setOnClickListener {
            selectPlatform { platform ->
                ShareUtils.shareText(requireContext(), platform, "大河向东流")
            }
        }
        viewBinding.btnShareImage.setOnClickListener {
            selectPlatform { platform ->
                ImagePicker.showSelectDialog(requireContext()) { res ->
                    if (res.isSuccessWithData()) {
                        val bitmap = BitmapFactory.decodeFile(res.getDataOrThrow())
                        ShareUtils.shareImage(requireContext(), platform, bitmap)
                    }
                }
            }
        }
    }

    private fun selectPlatform(callback: (ShareUtils.Platform) -> Unit) {
        val platforms = listOf(
            Pair(ShareUtils.Platform.WechatFriend, "微信好友"),
            Pair(ShareUtils.Platform.WechatMoments, "微信朋友圈"),
            Pair(ShareUtils.Platform.Weibo, "微博"),
        )
        showBottomItemsDialog(platforms.map { it.second }) { dialog, which ->
            callback(platforms[which].first)
        }
    }
}