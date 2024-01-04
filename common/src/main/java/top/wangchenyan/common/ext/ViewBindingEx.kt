package top.wangchenyan.common.ext

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

/**
 * Created by wcy on 2020/12/16.
 */

@MainThread
inline fun <reified VB : ViewBinding> Activity.viewBindings(): Lazy<VB> {
    return ViewBindingLazy(VB::class, { layoutInflater })
}

@MainThread
inline fun <reified VB : ViewBinding> Fragment.viewBindings(): Lazy<VB> {
    return ViewBindingLazy(VB::class, { layoutInflater })
}

val ViewBinding.context: Context
    get() = root.context

class ViewBindingLazy<VB : ViewBinding>(
    private val viewBindingClass: KClass<VB>,
    private var inflater: () -> LayoutInflater,
    private var viewGroup: ViewGroup? = null,
    private var attach: Boolean = false
) : Lazy<VB> {
    private var cached: VB? = null

    override val value: VB
        get() {
            val viewBinding = cached
            return if (viewBinding == null) {
                val method = viewBindingClass.java.getMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.java
                )
                (method.invoke(null, inflater.invoke(), viewGroup, attach) as VB).also {
                    cached = it
                }
            } else {
                viewBinding
            }
        }

    override fun isInitialized() = cached != null
}