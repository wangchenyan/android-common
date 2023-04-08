package me.wcy.common.widget.pager

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2

class CustomTabPager(
    private val lifecycle: Lifecycle,
    private val fragmentManager: FragmentManager,
    private val viewPager: ViewPager2
) : DefaultLifecycleObserver {
    private val fragmentList = mutableListOf<Fragment>()
    private val tabItemList = mutableListOf<View>()
    private var position = 0
    private var scrollable = true
    private var onTabEventListener: OnTabEventListener? = null
    private var hasSetup = false

    interface OnTabEventListener {
        fun onTabSelected(position: Int) {}

        fun onTabClick(position: Int): Boolean {
            return false
        }

        fun onTabClickAgain(position: Int) {}
    }

    fun addFragment(fragment: Fragment, tabItem: View) {
        if (!hasSetup) {
            fragmentList.add(fragment)
            tabItemList.add(tabItem)
        }
    }

    fun setOnTabEventListener(onTabClickListener: OnTabEventListener) {
        this.onTabEventListener = onTabClickListener
    }

    fun setPosition(position: Int) {
        this.position = position
        if (hasSetup) {
            setSelectedTab(position)
            viewPager.setCurrentItem(position, scrollable)
        }
    }

    fun setScrollable(scrollable: Boolean) {
        this.scrollable = scrollable
        viewPager.isUserInputEnabled = scrollable
    }

    fun setup() {
        val adapter = FragmentAdapter(lifecycle, fragmentManager, fragmentList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragmentList.size
        viewPager.setCurrentItem(position, scrollable)
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        lifecycle.addObserver(this)

        for (i in tabItemList.indices) {
            val tabItem = tabItemList[i]
            tabItem.setOnClickListener {
                if (i == viewPager.currentItem) {
                    onTabEventListener?.onTabClickAgain(i)
                } else if (onTabEventListener?.onTabClick(i) != true) {
                    setSelectedTab(i)
                    viewPager.setCurrentItem(i, scrollable)
                }
            }
        }

        setSelectedTab(position)
        hasSetup = true
    }

    fun getFragment(position: Int): Fragment? {
        return fragmentList.getOrNull(position)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setSelectedTab(position)
            onTabEventListener?.onTabSelected(position)
        }
    }

    private fun setSelectedTab(position: Int) {
        for (i in tabItemList.indices) {
            val view = tabItemList[i]
            view.isSelected = (i == position)
        }
    }
}
