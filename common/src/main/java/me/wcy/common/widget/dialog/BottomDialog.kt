package me.wcy.common.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import me.wcy.common.R
import me.wcy.common.databinding.CommonDialogBottomBinding
import kotlin.reflect.KClass

/**
 * @author wcy
 * @date 2018/7/21
 */
class BottomDialog(private val hostContext: Context) : BaseBottomDialog(hostContext) {
    private val viewBinding: CommonDialogBottomBinding
    private var contentBindingClazz: KClass<out ViewBinding>? = null
    private var onBindContent: ((BottomDialog, Any) -> Unit)? = null

    init {
        setContentView(R.layout.common_dialog_bottom)
        val rootView: View = findViewById(R.id.dialogBottomRoot)
        viewBinding = CommonDialogBottomBinding.bind(rootView)
        viewBinding.btnBottomDialogCancel.setOnClickListener {
            dismiss()
        }
        setContentView()
    }

    inline fun <reified VB : ViewBinding> setContentView(
        noinline onBind: (dialog: BottomDialog, viewBinding: VB) -> Unit
    ) = setContentView(VB::class, onBind)

    fun <VB : ViewBinding> setContentView(
        clazz: KClass<VB>,
        onBind: (dialog: BottomDialog, viewBinding: VB) -> Unit
    ) = apply {
        contentBindingClazz = clazz
        onBindContent = onBind as (BottomDialog, Any) -> Unit
    }

    private fun setContentView() {
        val clazz = contentBindingClazz ?: return
        val method = clazz.java.getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        )
        val contentViewBinding = method.invoke(
            null,
            LayoutInflater.from(hostContext),
            viewBinding.bottomDialogContent,
            true
        ) as ViewBinding?
        onBindContent?.invoke(this, contentViewBinding!!)
    }
}