package com.valentinhinov.ganandroidtest.feature.browser

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpacingDecoration(private val spacingPx: Int, private val spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex

        outRect.top = spacingPx
        outRect.left = if (spanIndex == 0) spacingPx else spacingPx / 2
        outRect.right = if (spanIndex == spanCount - 1) spacingPx else spacingPx / 2
    }
}