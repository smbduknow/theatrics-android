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

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel) {
        holder as ViewHolder
        holder.bind(item as UiEvent)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_event)) {
        fun bind(item: UiEvent) = with(itemView) {
            title.text = item.title.capitalize()
            description.text = Html.fromHtml(item.description.trim().capitalize())
            image.loadImg(item.image)
        }
    }
}