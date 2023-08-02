package top.wangchenyan.android_common.image

import android.graphics.BitmapFactory
import android.view.View
import me.wcy.common.ext.toast
import me.wcy.common.ext.viewBindings
import me.wcy.common.ui.fragment.BaseFragment
import me.wcy.common.utils.image.ImagePicker
import me.wcy.router.annotation.Route
import top.wangchenyan.android_common.databinding.FragmentImagePickerBinding

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