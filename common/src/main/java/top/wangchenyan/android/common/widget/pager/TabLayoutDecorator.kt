package top.wangchenyan.android.common.widget.pager

import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import top.wangchenyan.android.common.R

/**
 * 自定义 TabLayout 样式
 * Created by wangchenyan.top on 2022/11/7.
 */
class TabLayoutDecorator(val tabLayout: TabLayout) {
    private var textSize: Pair<Float, Float> = Pair(15f, 15f)
    private var textColor: Pair<Int, Int> = Pair(
        ContextCompat.getColor(tabLayout.context, R.color.common_text_h2_color),
        ContextCompat.getColor(tabLayout.context, R.color.common_text_h1_color)
    )

    init {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                tab?.getCustomText()?.apply {
                    isSelected = true
                    setTextSize(
                        TypedValue.COMPLEX_UNIT_DIP,
                        this@TabLayoutDecorator.textSize.second
                    )
                    setTextColor(textColor.second)
                    paint.isFakeBoldText = true
                }
            }

            override fun onTabUnselected(tab: Tab?) {
                tab?.getCustomText()?.apply {
                    isSelected = false
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, this@TabLayoutDecorator.textSize.first)
                    setTextColor(textColor.first)
                    paint.isFakeBoldText = false
                }
            }

            override fun onTabReselected(tab: Tab?) {
            }
        })
    }

    /**
     * 设置 Tab 字体大小，单位 dp
     */
    fun setTabTextSize(unselected: Float, selected: Float) = apply {
        textSize = Pair(unselected, selected)
    }

    /**
     * 设置 Tab 字体颜色
     */
    fun setTabTextColor(@ColorInt unselected: Int, @ColorInt selected: Int) = apply {
        textColor = Pair(unselected, selected)
    }

    /**
     * 添加 Tab
     */
    fun addTab(
        text: CharSequence?,
        @DrawableRes drawableStart: Int? = null,
        selected: Boolean = false
    ) = apply {
        tabLayout.addTab(tabLayout.newTab().also { tab ->
            initTab(tab, text, drawableStart)
            if (selected) {
                tab.select()
            }
        })
    }

    /**
     * 初始化 Tab，对 Tab 进行装饰
     */
    fun initTab(tab: Tab, text: CharSequence?, @DrawableRes drawableStart: Int? = null) {
        tab.customView = TextView(tabLayout.context).apply {
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.text = text
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, this@TabLayoutDecorator.textSize.first)
            setTextColor(textColor.first)
            if (drawableStart != null) {
                compoundDrawablePadding = SizeUtils.dp2px(5f)
                setCompoundDrawablesWithIntrinsicBounds(drawableStart, 0, 0, 0)
            }
        }
    }

    /**
     * 设置 Tab 文本
     */
    fun setTabText(position: Int, text: CharSequence?) {
        if (position >= 0 && position < tabLayout.tabCount) {
            tabLayout.getTabAt(position)?.getCustomText()?.text = text
        }
    }

    private fun Tab.getCustomText(): TextView? {
        return customView as? TextView
    }
}