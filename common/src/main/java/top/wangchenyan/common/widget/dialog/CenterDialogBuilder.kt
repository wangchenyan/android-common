package top.wangchenyan.common.widget.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

/**
 * Created by wangchenyan.top on 2022/9/28.
 */
class CenterDialogBuilder(internal val context: Context) {
    internal var width: Int? = null
    internal var title: CharSequence? = null
    internal var isTitleCenter: Boolean = true
    internal var isShowCloseView: Boolean = false
    internal var message: CharSequence? = null
    internal var isMessageCenter: Boolean = false
    internal var buttonTexts: List<CharSequence>? = null
    internal var onButtonClickListener: OnButtonClickListener? = null
    internal var contentBindingClazz: KClass<out ViewBinding>? = null
    internal var onBindContent: ((CenterDialog, Any) -> Unit)? = null
    internal var isCustomBg: Boolean = false
    internal var isCancelable: Boolean = true
    internal var isAutoClose: Boolean = true
    internal var onDismissListener: DialogInterface.OnDismissListener? = null

    fun width(value: Int?) = apply {
        width = value
    }

    fun title(value: CharSequence?) = apply {
        title = value
    }

    fun isTitleCenter(value: Boolean) = apply {
        isTitleCenter = value
    }

    fun isShowCloseView(value: Boolean) = apply {
        isShowCloseView = value
    }

    fun message(value: CharSequence?) = apply {
        message = value
    }

    fun isMessageCenter(value: Boolean) = apply {
        isMessageCenter = value
    }

    fun buttonText(value: List<CharSequence>?) = apply {
        buttonTexts = value
    }

    fun buttonText(vararg value: CharSequence) = apply {
        buttonTexts = value.asList()
    }

    fun onButtonClickListener(value: OnButtonClickListener) = apply {
        onButtonClickListener = value
    }

    inline fun <reified VB : ViewBinding> contentViewBinding(
        noinline value: (dialog: CenterDialog, viewBinding: VB) -> Unit
    ) = contentViewBinding(VB::class, value)

    fun <VB : ViewBinding> contentViewBinding(
        clazz: KClass<VB>,
        value: (dialog: CenterDialog, viewBinding: VB) -> Unit
    ) = apply {
        contentBindingClazz = clazz
        onBindContent = value as (CenterDialog, Any) -> Unit
    }

    fun cancelable(value: Boolean) = apply {
        isCancelable = value
    }

    fun isAutoClose(value: Boolean) = apply {
        isAutoClose = value
    }

    fun isCustomBg(value: Boolean) = apply {
        isCustomBg = value
    }

    fun onDismissListener(value: DialogInterface.OnDismissListener?) = apply {
        onDismissListener = value
    }

    fun build(): CenterDialog {
        return CenterDialog(this)
    }
}