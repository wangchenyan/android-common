package top.wangchenyan.android.common.sample

import android.graphics.BitmapFactory
import android.view.View
import top.wangchenyan.android.common.ext.toast
import top.wangchenyan.android.common.ext.viewBindings
import top.wangchenyan.android.common.ui.fragment.BaseFragment
import top.wangchenyan.android.common.utils.image.ImagePicker
import me.wcy.router.annotation.Route
import top.wangchenyan.android.common.sample.databinding.FragmentImagePickerBinding

/**
 * Created by wangchenyan.top on 2023/8/2.
 */
@Route("/image_picker")
class ImagePickerFragment : BaseFragment() {
    private val viewBinding by viewBindings<FragmentImagePickerBinding>()

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        viewBinding.btnSelect.setOnClickListener {
            ImagePicker.showSelectDialog(requireContext()) { res ->
                if (res.isSuccessWithData()) {
                    val bitmap = BitmapFactory.decodeFile(res.getDataOrThrow())
                    viewBinding.ivImage.setImageBitmap(bitmap)
                } else {
                    toast(res.msg)
                }
            }
        }
    }
}