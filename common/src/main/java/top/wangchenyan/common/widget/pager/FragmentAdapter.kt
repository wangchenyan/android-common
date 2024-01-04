package top.wangchenyan.common.widget.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(
    lifecycle: Lifecycle,
    fm: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val titleList: List<CharSequence?>? = null
) : FragmentStateAdapter(fm, lifecycle) {

    fun getPageTitle(position: Int): CharSequence? {
        if (titleList?.getOrNull(position)?.isNotEmpty() == true) {
            return titleList.getOrNull(position)
        }
        val fragment = fragmentList[position]
        if (fragment is IPagerFragment) {
            return fragment.getTabText()
        }
        return ""
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
