package top.wangchenyan.common.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.immersionbar.ImmersionBar
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.R
import top.wangchenyan.common.databinding.CommonTitleLayoutBinding
import top.wangchenyan.common.databinding.CommonTitleMenuButtonBinding
import top.wangchenyan.common.databinding.CommonTitleMenuImageBinding
import top.wangchenyan.common.databinding.CommonTitleMenuTextBinding
import top.wangchenyan.common.utils.AndroidVersionUtils
import top.wangchenyan.common.utils.StatusBarUtils
import kotlin.math.max
import kotlin.math.min

class TitleLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val viewBinding: CommonTitleLayoutBinding
    private val scrollThreshold: Float
    private val updateTitleAlphaWhenScroll: Boolean
    private var startTextStyle: TextStyle = TextStyle.AUTO
    private var endTextStyle: TextStyle = TextStyle.AUTO
    private var currTextStyle: TextStyle = TextStyle.AUTO
    private var contentView: View? = null

    sealed class TextStyle(val value: Int) {
        object AUTO : TextStyle(0)
        object BLACK : TextStyle(1)
        object WHITE : TextStyle(2)

        companion object {
            fun valueOf(value: Int): TextStyle {
                return when (value) {
                    1 -> BLACK
                    2 -> WHITE
                    else -> AUTO
                }
            }
        }
    }

    init {
        id = R.id.common_title_layout
        orientation = VERTICAL
        isClickable = true
        viewBinding = CommonTitleLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout)
        val startTextStyleStr = ta.getInt(R.styleable.TitleLayout_tlTextStyle, 0)
        startTextStyle = TextStyle.valueOf(startTextStyleStr)
        val endTextStyleStr = ta.getInt(R.styleable.TitleLayout_tlTextStyleAfterScroll, 0)
        endTextStyle = TextStyle.valueOf(endTextStyleStr)
        val scrollViewId = ta.getResourceId(R.styleable.TitleLayout_tlScrollViewId, 0)
        scrollThreshold = ta.getDimension(
            R.styleable.TitleLayout_tlScrollThreshold,
            SizeUtils.dp2px(100f).toFloat()
        )
        updateTitleAlphaWhenScroll =
            ta.getBoolean(R.styleable.TitleLayout_tlUpdateTitleAlphaWhenScroll, false)
        val isJustShowStatus = ta.getBoolean(R.styleable.TitleLayout_tlJustShowStatusBar, false)
        val isShowBack = ta.getBoolean(R.styleable.TitleLayout_tlShowBack, true)
        val isBackCloseStyle = ta.getBoolean(R.styleable.TitleLayout_tlBackCloseStyle, false)
        val contentLayoutId = ta.getResourceId(
            R.styleable.TitleLayout_tlTitleContentLayout,
            R.layout.common_title_content_default
        )
        val titleText = ta.getString(R.styleable.TitleLayout_tlTitleText)
        val isTitleCenter = try {
            ta.getBooleanOrThrow(R.styleable.TitleLayout_tlTitleCenter)
        } catch (e: Exception) {
            getTitleConfig().isTitleCenter
        }
        var backgroundDrawable: Drawable? = null
        var backgroundColor: Int? = null
        if (ta.hasValue(R.styleable.TitleLayout_tlBackgroundDrawable)) {
            backgroundDrawable = ta.getDrawable(R.styleable.TitleLayout_tlBackgroundDrawable)
        } else if (ta.hasValue(R.styleable.TitleLayout_tlBackgroundColor)) {
            backgroundColor =
                ta.getColor(R.styleable.TitleLayout_tlBackgroundColor, Color.TRANSPARENT)
        }
        ta.recycle()

        ActivityUtils.getActivityByContext(context)?.apply {
            if (StatusBarUtils.isSupportStatusBarTransparent()) {
                StatusBarUtils.getStatusBarHeight(this) { height ->
                    setPadding(0, height, 0, 0)
                }
            }
        }

        if (backgroundDrawable != null) {
            background = backgroundDrawable
        } else if (backgroundColor != null) {
            setBackgroundColor(backgroundColor)
        }

        if (isJustShowStatus.not()) {
            viewBinding.root.isVisible = true
            setContentView(contentLayoutId)
            setTitleText(titleText ?: ActivityUtils.getActivityByContext(context)?.title)
            setShowBack(isShowBack)
            setBackCloseStyle(isBackCloseStyle)
            viewBinding.ivBack.setOnClickListener {
                ActivityUtils.getActivityByContext(context)?.onBackPressed()
            }
            setTitleCenter(isTitleCenter)
        } else {
            viewBinding.root.isVisible = false
        }

        setCurrTextStyle(startTextStyle)

        post { bindScrollView(scrollViewId) }
    }

    fun setCurrTextStyle(style: TextStyle) {
        currTextStyle = style
        applyTextStyle()
    }

    fun applyTextStyle() {
        val statusBarDarkFont: Boolean = when (currTextStyle) {
            TextStyle.BLACK -> true
            TextStyle.WHITE -> false
            else -> getTitleConfig().isStatusBarDarkFontWhenAuto()
        }
        ActivityUtils.getActivityByContext(context)?.apply {
            ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont)
                .init()
        }
        val textColor = getTextColor()
        getTitleTextView()?.setTextColor(textColor)
        viewBinding.ivBack.imageTintList = textColor
        viewBinding.leftMenuContainer.forEach { menuView ->
            if (menuView is ImageView && menuView.getTag(R.id.common_tl_menu_day_night) == true) {
                menuView.imageTintList = textColor
            } else if (menuView is TextView && menuView !is Button) {
                menuView.setTextColor(textColor)
            }
        }
        viewBinding.rightMenuContainer.forEach { menuView ->
            if (menuView is ImageView && menuView.getTag(R.id.common_tl_menu_day_night) == true) {
                menuView.imageTintList = textColor
            } else if (menuView is TextView && menuView !is Button) {
                menuView.setTextColor(textColor)
            }
        }
    }

    fun setShowBack(show: Boolean) {
        viewBinding.ivBack.isVisible = show
    }

    fun setBackCloseStyle(isCloseStyle: Boolean) {
        viewBinding.ivBack.setImageResource(
            if (isCloseStyle) {
                getTitleConfig().closeIcon()
            } else {
                getTitleConfig().backIcon()
            }
        )
    }

    fun getTitleTextView(): TextView? {
        return findViewById(R.id.common_title_text_view) as? TextView
    }

    fun setTitleText(@StringRes resId: Int) {
        getTitleTextView()?.setText(resId)
    }

    fun setTitleText(title: CharSequence?) {
        getTitleTextView()?.text = title
    }

    fun getContentView(): View? {
        return contentView
    }

    fun setContentView(@LayoutRes resId: Int) {
        contentView = LayoutInflater.from(context).inflate(resId, viewBinding.titleContent, false)
        viewBinding.titleContent.removeAllViews()
        viewBinding.titleContent.addView(contentView)
    }

    fun setTitleCenter(isCenter: Boolean) {
        val contentLp = viewBinding.titleContent.layoutParams as RelativeLayout.LayoutParams
        contentLp.removeRule(RelativeLayout.END_OF)
        contentLp.removeRule(RelativeLayout.START_OF)
        if (isCenter.not()) {
            contentLp.addRule(RelativeLayout.END_OF, R.id.leftMenuContainer)
            contentLp.addRule(RelativeLayout.START_OF, R.id.rightMenuContainer)
        }

        val contentView = getContentView()
        if (contentView?.id == R.id.common_title_text_view) {
            val titleLp = contentView.layoutParams as FrameLayout.LayoutParams
            titleLp.gravity = if (isCenter) Gravity.CENTER else Gravity.CENTER_VERTICAL
        }
        requestLayout()
    }

    fun bindScrollView(viewId: Int) {
        if (viewId <= 0) {
            return
        }
        val view = rootView.findViewById<View>(viewId)
        if (view is NestedScrollView) {
            updateScroll(view.scrollY)
            view.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                updateScroll(scrollY)
            }
        } else if (AndroidVersionUtils.isAboveOrEqual6()) {
            updateScroll(view.scrollY)
            view.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                updateScroll(scrollY)
            }
        }
    }

    fun updateScroll(scrollY: Int) {
        var alpha = scrollY.toFloat() / scrollThreshold
        alpha = max(0f, alpha)
        alpha = min(1f, alpha)
        background?.alpha = (alpha * 255).toInt()
        if (updateTitleAlphaWhenScroll) {
            getTitleTextView()?.alpha = alpha
        }
        setCurrTextStyle(if (alpha > 0.5f) endTextStyle else startTextStyle)
    }

    fun addTextMenu(text: CharSequence, isLeft: Boolean = false): TextView {
        val container = getMenuContainer(isLeft)
        val textBinding = CommonTitleMenuTextBinding.inflate(
            LayoutInflater.from(context), container, true
        )
        textBinding.root.text = text
        textBinding.root.setTextColor(getTextColor())
        return textBinding.root
    }

    fun addButtonMenu(text: CharSequence, isLeft: Boolean = false): TextView {
        val container = getMenuContainer(isLeft)
        val buttonBinding = CommonTitleMenuButtonBinding.inflate(
            LayoutInflater.from(context), container, true
        )
        buttonBinding.root.text = text
        return buttonBinding.root
    }

    fun addImageMenu(imageId: Int, isDayNight: Boolean = true, isLeft: Boolean = false): ImageView {
        val container = getMenuContainer(isLeft)
        val imageBinding = CommonTitleMenuImageBinding.inflate(
            LayoutInflater.from(context), container, true
        )
        imageBinding.root.setImageResource(imageId)
        if (isDayNight) {
            imageBinding.root.setTag(R.id.common_tl_menu_day_night, true)
        }
        return imageBinding.root
    }

    fun addViewMenu(layoutId: Int, isLeft: Boolean = false): View {
        val container = getMenuContainer(isLeft)
        val view = LayoutInflater.from(context).inflate(layoutId, container, false)
        viewBinding.rightMenuContainer.addView(view)
        return view
    }

    fun removeAllMenu() {
        viewBinding.leftMenuContainer.removeAllViews()
        viewBinding.rightMenuContainer.removeAllViews()
    }

    private fun getMenuContainer(isLeft: Boolean): ViewGroup {
        return if (isLeft) {
            viewBinding.leftMenuContainer
        } else {
            viewBinding.rightMenuContainer
        }
    }

    private fun getTextColor(): ColorStateList {
        val colorResId = when (currTextStyle) {
            TextStyle.BLACK -> getTitleConfig().textColorBlack()
            TextStyle.WHITE -> getTitleConfig().textColorWhite()
            TextStyle.AUTO -> getTitleConfig().textColorAuto()
        }
        return AppCompatResources.getColorStateList(context, colorResId)
    }

    private fun getTitleConfig(): TitleLayoutConfig {
        return try {
            CommonApp.config.titleLayoutConfig
        } catch (t: Throwable) {
            TitleLayoutConfig.Builder().build()
        }
    }
}