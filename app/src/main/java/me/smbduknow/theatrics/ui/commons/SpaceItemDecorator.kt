package me.smbduknow.theatrics.ui.commons

import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View
import me.smbduknow.theatrics.R

class SpaceItemDecorator(
        val orientation: Int,
        @DimenRes val dimenSpaceRes: Int = R.dimen.space_normal
) : RecyclerView.ItemDecoration() {

    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val pos = parent.getChildAdapterPosition(view)
        val space = view.context.resources.getDimensionPixelSize(dimenSpaceRes);
        if (pos <= 0) return
        when (orientation) {
            VERTICAL ->  outRect.top = space
            HORIZONTAL -> outRect.left = space
        }
    }
}