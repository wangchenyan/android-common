package top.wangchenyan.common.widget.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

/**
 * Created by wangchenyan.top on 2023/1/2.
 */
class BottomDialogBuilder(internal val context: Context) {
    internal var contentBindingClazz: KClass<out ViewBinding>? = null
    internal var onBindContent: ((BottomDialog, Any) -> Unit)? = null
    internal var isCancelable: Boolean = true
    internal var onDismissListener: DialogInterface.OnDismissListener? = null

    inline fun <reified VB : ViewBinding> contentViewBinding(
        noinline value: (dialog: BottomDialog, viewBinding: VB) -> Unit
    ) = contentViewBinding(VB::class, value)

    fun <VB : ViewBinding> contentViewBinding(
        clazz: KClass<VB>,
        value: (dialog: BottomDialog, viewBinding: VB) -> Unit
    ) = apply {
        contentBindingClazz = clazz
        onBindContent = value as (BottomDialog, Any) -> Unit
    }

    fun cancelable(value: Boolean) = apply {
        isCancelable = value
    }

    fun onDismissListener(value: DialogInterface.OnDismissListener?) = apply {
        onDismissListener = value
    }

    fun build(): BottomDialog {
        return BottomDialog(this)
    }
}