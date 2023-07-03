package me.wcy.common.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by wangchenyan.top on 2022/3/24.
 */
class GridSpacingDecoration(
    private val horizontalSpacing: Int,
    private val verticalSpacing: Int,
) : RecyclerView.ItemDecoration() {
    private var spanCount: Int = 1
    private var itemCount: Int = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
            itemCount = parent.adapter?.itemCount ?: 0

            val position = parent.getChildAdapterPosition(view)
            if (isSingleColumn()) {
                outRect.left = 0
                outRect.right = 0
            } else if (isFirstColumn(position)) {
                outRect.left = 0
                outRect.right = horizontalSpacing / 2
            } else if (isLastColumn(position)) {
                outRect.left = horizontalSpacing / 2
                outRect.right = 0
            } else {
                outRect.left = horizontalSpacing / 2
                outRect.right = horizontalSpacing / 2
            }

            if (isSingleRow()) {
                outRect.top = 0
                outRect.bottom = 0
            } else if (isFirstRow(position)) {
                outRect.top = 0
                outRect.bottom = verticalSpacing / 2
            } else if (isLastRow(position)) {
                outRect.top = verticalSpacing / 2
                outRect.bottom = 0
            } else {
                outRect.top = verticalSpacing / 2
                outRect.bottom = verticalSpacing / 2
            }
        }
    }

    private fun isSingleColumn(): Boolean {
        return spanCount == 1
    }

    private fun isFirstColumn(position: Int): Boolean {
        return position % spanCount == 0
    }

    private fun isLastColumn(position: Int): Boolean {
        return position % spanCount == spanCount - 1
    }

    private fun isSingleRow(): Boolean {
        return itemCount <= spanCount
    }

    private fun isFirstRow(position: Int): Boolean {
        return position < spanCount
    }

    private fun isLastRow(position: Int): Boolean {
        return position / spanCount == (itemCount - 1) / spanCount
    }
}