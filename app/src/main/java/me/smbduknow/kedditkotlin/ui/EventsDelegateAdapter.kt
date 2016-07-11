package me.smbduknow.kedditkotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_news.view.*
import me.smbduknow.kedditkotlin.R
import me.smbduknow.kedditkotlin.ui.commons.adapter.ViewModel
import me.smbduknow.kedditkotlin.ui.commons.adapter.ViewModelDelegateAdapter
import me.smbduknow.kedditkotlin.ui.commons.inflate
import me.smbduknow.kedditkotlin.ui.model.UiEvent

class EventsDelegateAdapter : ViewModelDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel) {
        holder as ViewHolder
        holder.bind(item as UiEvent)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_news)) {
        fun bind(item: UiEvent) = with(itemView) {
            title.text = item.title
            description.text = item.description
        }
    }
}