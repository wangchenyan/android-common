package top.wangchenyan.common.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by wangchenyan.top on 2023/7/14.
 */
class SpacingDecoration(private val spacing: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val position = parent.getChildAdapterPosition(view)
            val space = if (position > 0) spacing else 0
            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = space
            } else {
                outRect.left = space
            }
        }
    }
}