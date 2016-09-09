package me.smbduknow.theatrics.ui.adapter.delegate

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_event.view.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.commons.adapter.ViewModel
import me.smbduknow.theatrics.ui.commons.adapter.ViewModelDelegateAdapter
import me.smbduknow.theatrics.ui.commons.inflate
import me.smbduknow.theatrics.ui.commons.loadImg
import me.smbduknow.theatrics.ui.model.UiFeedEvent

class EventsDelegateAdapter : ViewModelDelegateAdapter {

    var listener: (position: Int) -> Unit = {}

    override fun isForViewType(item: ViewModel, position: Int) = item is UiFeedEvent

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel, position: Int) {
        holder as ViewHolder
        holder.bind(item as UiFeedEvent, position, listener)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_event)) {
        fun bind(item: UiFeedEvent, position: Int, listener: (position: Int) -> Unit) {
            with(itemView) {
                item_title.text = item.title.capitalize()
                item_place.text = Html.fromHtml(item.place.trim().capitalize())
                item_date.text = item.date
                item_month.text = item.month
                item_image.loadImg(item.image)
                setOnClickListener { v -> listener(position) }
            }
        }
    }

}