package me.wcy.common.widget.pager

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutPager(
    private val lifecycle: Lifecycle,
    private val fragmentManager: FragmentManager,
    private val viewPager: ViewPager2,
    private val tabLayout: TabLayout
) : DefaultLifecycleObserver {
    private val fragmentList = mutableListOf<Fragment>()
    private val titleList = mutableListOf<CharSequence?>()
    private val titleDrawableList = mutableListOf<Int?>()
    private var position = 0
    private var hasSetup = false

    private val tabLayoutDecorator by lazy { TabLayoutDecorator(tabLayout) }

    var onTabChangedListener: ((position: Int) -> Unit)? = null

    fun addFragment(
        fragment: Fragment,
        title: CharSequence? = null,
        @DrawableRes drawableStart: Int? = null
    ) {
        if (!hasSetup) {
            fragmentList.add(fragment)
            titleList.add(title)
            titleDrawableList.add(drawableStart)
        }
    }

    fun getFragment(position: Int): Fragment? {
        return fragmentList.getOrNull(position)
    }

    fun getCurrentFragment(): Fragment? {
        if (fragmentList.isNotEmpty()) {
            return fragmentList[position]
        }
        return null
    }

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
        if (hasSetup) {
            viewPager.currentItem = position
        }
    }

    fun setTabText(position: Int, text: CharSequence) {
        tabLayoutDecorator.setTabText(position, text)
    }

    /**
     * 设置 tab 字体大小，单位 dp
     */
    fun setTabTextSize(unselected: Float, selected: Float) {
        tabLayoutDecorator.setTabTextSize(unselected, selected)
    }

    fun setTabTextColor(@ColorInt unselected: Int, @ColorInt selected: Int) {
        tabLayoutDecorator.setTabTextColor(unselected, selected)
    }

    fun setup() {
        val adapter = FragmentAdapter(lifecycle, fragmentManager, fragmentList, titleList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragmentList.size
        viewPager.currentItem = position
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        lifecycle.addObserver(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tabLayoutDecorator.initTab(
                tab,
                adapter.getPageTitle(position),
                titleDrawableList.getOrNull(position)
            )
        }.attach()

        hasSetup = true
    }

    fun size(): Int {
        return fragmentList.size
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@TabLayoutPager.position = position
            onTabChangedListener?.invoke(position)
        }
    }
}
