package me.wcy.common.widget.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import me.wcy.common.R
import me.wcy.common.databinding.CommonDialogCenterBinding
import kotlin.reflect.KClass

/**
 * Created by wangchenyan.top on 2022/9/28.
 */
class CenterDialog internal constructor(builder: CenterDialogBuilder) :
    BaseCenterDialog(builder.context, builder.width) {
    private val hostContext = builder.context
    private val title: CharSequence?
    private val isTitleCenter: Boolean
    private val isShowCloseView: Boolean
    private val message: CharSequence?
    private val isMessageCenter: Boolean
    private val buttonTexts: List<CharSequence>?
    private val onButtonClickListener: OnButtonClickListener?
    private val contentBindingClazz: KClass<out ViewBinding>?
    private val onBindContent: ((CenterDialog, Any) -> Unit)?
    private val isCustomBg: Boolean
    private val isAutoClose: Boolean

    private val viewBinding: CommonDialogCenterBinding

    private var contentViewBinding: ViewBinding? = null

    init {
        title = builder.title
        isTitleCenter = builder.isTitleCenter
        isShowCloseView = builder.isShowCloseView
        message = builder.message
        isMessageCenter = builder.isMessageCenter
        buttonTexts = builder.buttonTexts
        onButtonClickListener = builder.onButtonClickListener
        contentBindingClazz = builder.contentBindingClazz
        onBindContent = builder.onBindContent
        isCustomBg = builder.isCustomBg
        isAutoClose = builder.isAutoClose

        setCancelable(builder.isCancelable)
        setOnDismissListener(builder.onDismissListener)

        setContentView(R.layout.common_dialog_center)
        val rootView: View = findViewById(R.id.dialogCommonRoot)
        viewBinding = CommonDialogCenterBinding.bind(rootView)
        if (isCustomBg) {
            rootView.background = null
        }
        initTitle()
        initMessage()
        initCloseView()
        initContentView()
        initButton()
    }

    fun <VB : ViewBinding> getContentViewBinding(): VB? {
        return contentViewBinding as? VB
    }

    private fun initTitle() {
        if (title?.isNotEmpty() == true) {
            viewBinding.tvDialogTitle.isVisible = true
            viewBinding.tvDialogTitle.text = title
            val lp = viewBinding.tvDialogTitle.layoutParams as FrameLayout.LayoutParams
            lp.gravity = if (isTitleCenter) Gravity.CENTER_HORIZONTAL else Gravity.START
            viewBinding.tvDialogTitle.layoutParams = lp
        } else {
            viewBinding.tvDialogTitle.isVisible = false
        }
    }

    private fun initMessage() {
        if (message?.isNotEmpty() == true) {
            viewBinding.tvDialogMessage.isVisible = true
            viewBinding.tvDialogMessage.text = message
            viewBinding.tvDialogMessage.gravity = if (isMessageCenter) {
                Gravity.CENTER_HORIZONTAL
            } else {
                Gravity.START
            }
        } else {
            viewBinding.tvDialogMessage.isVisible = false
        }
    }

    private fun initCloseView() {
        if (isShowCloseView) {
            viewBinding.vDialogClose.isVisible = true
            viewBinding.vDialogClose.setOnClickListener {
                dismiss()
            }
        } else {
            viewBinding.vDialogClose.isVisible = false
        }
    }

    private fun initContentView() {
        if (contentBindingClazz != null) {
            viewBinding.contentViewContainer.isVisible = true
            viewBinding.contentViewContainer.removeAllViews()
            val method = contentBindingClazz.java.getMethod(
                "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
            )
            contentViewBinding = method.invoke(
                null,
                LayoutInflater.from(hostContext),
                viewBinding.contentViewContainer,
                true
            ) as ViewBinding?
            onBindContent?.invoke(this, contentViewBinding!!)
        } else {
            viewBinding.contentViewContainer.isVisible = false
        }
    }

    private fun initButton() {
        fun onButtonClick(which: Int) {
            onButtonClickListener?.invoke(this, which)
            if (isAutoClose) {
                dismiss()
            }
        }

        if (buttonTexts?.first()?.isNotEmpty() == true) {
            viewBinding.btnDialogPositive.isVisible = true
            viewBinding.btnDialogPositive.text = buttonTexts.first()
            viewBinding.btnDialogPositive.setOnClickListener {
                onButtonClick(0)
            }
        } else {
            viewBinding.btnDialogPositive.isVisible = false
        }

        if (buttonTexts?.getOrNull(1)?.isNotEmpty() == true) {
            viewBinding.btnDialogNegative.isVisible = true
            viewBinding.btnDialogNegative.text = buttonTexts[1]
            viewBinding.btnDialogNegative.setOnClickListener {
                onButtonClick(1)
            }
        } else {
            viewBinding.btnDialogNegative.isVisible = false
        }
    }
}