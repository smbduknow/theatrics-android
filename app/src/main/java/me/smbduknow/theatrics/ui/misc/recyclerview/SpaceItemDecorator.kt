package me.smbduknow.theatrics.ui.misc.recyclerview

import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View
import me.smbduknow.theatrics.R

class SpaceItemDecorator(
        val orientation: Int,
        @android.support.annotation.DimenRes val dimenSpaceRes: Int = me.smbduknow.theatrics.R.dimen.space_normal
) : android.support.v7.widget.RecyclerView.ItemDecoration() {

    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
    }

    override fun getItemOffsets(outRect: android.graphics.Rect, view: android.view.View, parent: android.support.v7.widget.RecyclerView, state: android.support.v7.widget.RecyclerView.State?) {
        val space = view.context.resources.getDimensionPixelSize(dimenSpaceRes)
        when (orientation) {
            me.smbduknow.theatrics.ui.misc.recyclerview.SpaceItemDecorator.Companion.VERTICAL ->  outRect.top = space
            me.smbduknow.theatrics.ui.misc.recyclerview.SpaceItemDecorator.Companion.HORIZONTAL -> outRect.left = space
        }
    }
}