package me.smbduknow.theatrics.ui.feed.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.model.UiCity

class CityArrayAdapter(
        val ctx: Context,
        val resource: Int,
        val objects: List<UiCity>
) : ArrayAdapter<UiCity>(ctx, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): TextView {
        val v = super.getView(position, convertView, parent) as TextView
        v.text = objects[position].title
        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): TextView {
        val v = super.getView(position, convertView, parent) as TextView
        v.text = objects[position].title
        val padding = ctx.resources.getDimensionPixelSize(R.dimen.space_normal)
        v.setPadding(padding, padding, padding, padding)
        return v
    }

    fun findBySlug(slug: String) = objects.find { it.slug.equals(slug) }
    fun findPositionBySlug(slug: String) = objects.indexOf(findBySlug(slug))

}