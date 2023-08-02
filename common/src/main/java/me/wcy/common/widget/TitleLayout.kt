package me.wcy.common.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.immersionbar.ImmersionBar
import me.wcy.common.CommonApp
import me.wcy.common.R
import me.wcy.common.databinding.CommonTitleLayoutBinding
import me.wcy.common.databinding.CommonTitleMenuButtonBinding
import me.wcy.common.databinding.CommonTitleMenuImageBinding
import me.wcy.common.databinding.CommonTitleMenuTextBinding
import me.wcy.common.utils.AndroidVersionUtils
import me.wcy.common.utils.StatusBarUtils
import kotlin.math.max
import kotlin.math.min

class TitleLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: CommonTitleLayoutBinding
    private val scrollThreshold: Float
    private var startTextStyle = TextStyle.AUTO
    private var endTextStyle = TextStyle.AUTO
    private var textStyle = TextStyle.AUTO
    private var contentView: View? = null

    enum class TextStyle(val value: Int) {
        AUTO(0),
        BLACK(1),
        WHITE(2);

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
        binding = CommonTitleLayoutBinding.inflate(LayoutInflater.from(context), this, true)

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
        val isJustShowStatus = ta.getBoolean(R.styleable.TitleLayout_tlJustShowStatusBar, false)
        val isShowBack = ta.getBoolean(R.styleable.TitleLayout_tlShowBack, true)
        val isBackCloseStyle = ta.getBoolean(R.styleable.TitleLayout_tlBackCloseStyle, false)
        val contentLayoutId = ta.getResourceId(
            R.styleable.TitleLayout_tlTitleContentLayout,
            R.layout.common_title_content_default
        )
        val titleText = ta.getString(R.styleable.TitleLayout_tlTitleText)
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
            binding.titleContent.isVisible = true
            setContentView(contentLayoutId)
            setTitleText(titleText ?: ActivityUtils.getActivityByContext(context)?.title)
            setShowBack(isShowBack)
            setBackCloseStyle(isBackCloseStyle)
            binding.ivTitleLayoutBack.setOnClickListener {
                ActivityUtils.getActivityByContext(context)?.onBackPressed()
            }
        } else {
            binding.titleContent.isVisible = false
        }

        setTextStyle(startTextStyle)

        post { bindScrollView(scrollViewId) }
    }

    fun setTextStyle(style: TextStyle) {
        textStyle = style
        updateTextStyle()
    }

    fun updateTextStyle() {
        val statusBarDarkFont: Boolean = when (textStyle) {
            TextStyle.BLACK -> true
            TextStyle.WHITE -> false
            else -> getTitleConfig().isStatusBarDarkFontWhenAuto
        }
        ActivityUtils.getActivityByContext(context)?.apply {
            ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont)
                .init()
        }
        val textColor = getTextColor()
        getTitleTextView()?.setTextColor(textColor)
        binding.ivTitleLayoutBack.imageTintList = textColor
        for (i in 0 until binding.titleLayoutMenuContainer.childCount) {
            val menuView = binding.titleLayoutMenuContainer.getChildAt(i)
            if (menuView is ImageView && menuView.getTag(R.id.common_tl_menu_day_night) == true) {
                menuView.imageTintList = textColor
            } else if (menuView is TextView && menuView !is Button) {
                menuView.setTextColor(textColor)
            }
        }
    }

    fun setShowBack(show: Boolean) {
        binding.ivTitleLayoutBack.isVisible = show
    }

    fun setBackCloseStyle(isCloseStyle: Boolean) {
        binding.ivTitleLayoutBack.setImageResource(
            if (isCloseStyle) {
                getTitleConfig().backIcon
            } else {
                getTitleConfig().backIcon
            }
        )
    }

    fun getTitleTextView(): TextView? {
        return findViewById(R.id.tvDefaultTitle) as? TextView
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
        contentView = LayoutInflater.from(context).inflate(resId, binding.titleLayoutContent, false)
        binding.titleLayoutContent.removeAllViews()
        binding.titleLayoutContent.addView(contentView)
    }

    fun bindScrollView(viewId: Int) {
        if (viewId <= 0) {
            return
        }
        val view = rootView.findViewById<View>(viewId)
        if (view is ScrollView && AndroidVersionUtils.isAboveOrEqual6()) {
            updateTextStyleByScroll(view.scrollY)
            view.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                updateTextStyleByScroll(scrollY)
            }
        } else if (view is NestedScrollView) {
            updateTextStyleByScroll(view.scrollY)
            view.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                updateTextStyleByScroll(scrollY)
            }
        }
    }

    private fun updateTextStyleByScroll(scrollY: Int) {
        var alpha = scrollY.toFloat() / scrollThreshold
        alpha = max(0f, alpha)
        alpha = min(1f, alpha)
        background?.alpha = (alpha * 255).toInt()
        setTextStyle(if (alpha > 0.5f) endTextStyle else startTextStyle)
    }

    fun addTextMenu(text: CharSequence): TextView {
        val textBinding = CommonTitleMenuTextBinding.inflate(
            LayoutInflater.from(context),
            binding.titleLayoutMenuContainer,
            false
        )
        textBinding.root.text = text
        textBinding.root.setTextColor(getTextColor())
        binding.titleLayoutMenuContainer.addView(textBinding.root)
        return textBinding.root
    }

    fun addButtonMenu(text: CharSequence): TextView {
        val buttonBinding = CommonTitleMenuButtonBinding.inflate(
            LayoutInflater.from(context),
            binding.titleLayoutMenuContainer,
            false
        )
        buttonBinding.root.text = text
        binding.titleLayoutMenuContainer.addView(buttonBinding.root)
        return buttonBinding.root
    }

    fun addImageMenu(imageId: Int, isDayNight: Boolean = true): ImageView {
        val imageBinding = CommonTitleMenuImageBinding.inflate(
            LayoutInflater.from(context),
            binding.titleLayoutMenuContainer,
            false
        )
        imageBinding.root.setImageResource(imageId)
        if (isDayNight) {
            imageBinding.root.setTag(R.id.common_tl_menu_day_night, true)
        }
        binding.titleLayoutMenuContainer.addView(imageBinding.root)
        return imageBinding.root
    }

    fun addViewMenu(layoutId: Int): View {
        val view =
            LayoutInflater.from(context).inflate(layoutId, binding.titleLayoutMenuContainer, false)
        binding.titleLayoutMenuContainer.addView(view)
        return view
    }

    fun removeAllMenu() {
        binding.titleLayoutMenuContainer.removeAllViews()
    }

    private fun getTextColor(): ColorStateList {
        val colorResId = when (textStyle) {
            TextStyle.BLACK -> getTitleConfig().textColorBlack
            TextStyle.WHITE -> getTitleConfig().textColorWhite
            TextStyle.AUTO -> getTitleConfig().textColorAuto
        }
        return AppCompatResources.getColorStateList(context, colorResId)
    }

    private fun getTitleConfig(): TitleLayoutConfig {
        return try {
            CommonApp.config.title
        } catch (t: Throwable) {
            DefaultTitleLayoutConfig()
        }
    }

    interface TitleLayoutConfig {
        val isStatusBarDarkFontWhenAuto: Boolean

        @get:ColorRes
        val textColorBlack: Int

        @get:ColorRes
        val textColorWhite: Int

        @get:ColorRes
        val textColorAuto: Int

        @get:DrawableRes
        val backIcon: Int
    }

    class DefaultTitleLayoutConfig : TitleLayoutConfig {
        override val isStatusBarDarkFontWhenAuto: Boolean
            get() = true
        override val textColorBlack: Int
            get() = R.color.common_text_h1_color
        override val textColorWhite: Int
            get() = R.color.white
        override val textColorAuto: Int
            get() = R.color.common_text_h1_color
        override val backIcon: Int
            get() = R.drawable.common_ic_title_back
    }
}