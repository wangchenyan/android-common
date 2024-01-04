package top.wangchenyan.common.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

/**
 * @author wcy
 * @date 2018/7/21
 */
class BottomDialog internal constructor(builder: BottomDialogBuilder) :
    BaseBottomDialog(builder.context) {
    private val contentBindingClazz: KClass<out ViewBinding>?
    private val onBindContent: ((BottomDialog, Any) -> Unit)?

    init {
        contentBindingClazz = builder.contentBindingClazz
        onBindContent = builder.onBindContent
        setCancelable(builder.isCancelable)
        setOnDismissListener(builder.onDismissListener)

        if (contentBindingClazz != null) {
            val contentBinding = getContentViewBinding(builder.context, contentBindingClazz)
            setContentView(contentBinding.root)
            onBindContent?.invoke(this, contentBinding)
        }
    }

    private fun getContentViewBinding(
        context: Context,
        contentBindingClazz: KClass<out ViewBinding>
    ): ViewBinding {
        val method = contentBindingClazz.java.getMethod(
            "inflate", LayoutInflater::class.java
        )
        return method.invoke(null, LayoutInflater.from(context)) as ViewBinding
    }
}