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
import me.smbduknow.theatrics.ui.model.UiEvent

class EventsDelegateAdapter : ViewModelDelegateAdapter {

    var listener: (position: Int) -> Unit = {}

    override fun isForViewType(item: ViewModel, position: Int) = item is UiEvent

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel, position: Int) {
        holder as ViewHolder
        holder.bind(item as UiEvent, position, listener)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_event)) {
        fun bind(item: UiEvent, position: Int, listener: (position: Int) -> Unit) {
            with(itemView) {
                title.text = item.title.capitalize()
                description.text = Html.fromHtml(item.description.trim().capitalize())
                image.loadImg(item.image)
                setOnClickListener { v -> listener(position) }
            }
        }
    }

}