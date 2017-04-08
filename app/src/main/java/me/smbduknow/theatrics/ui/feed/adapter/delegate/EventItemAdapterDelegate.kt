package me.smbduknow.theatrics.ui.feed.adapter.delegate

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_event.view.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.misc.inflate
import me.smbduknow.theatrics.ui.misc.loadImg
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import me.smbduknow.theatrics.ui.model.UiModel

class EventItemAdapterDelegate : AdapterDelegate<ArrayList<UiModel>>() {

    var listener: (position: Int) -> Unit = {}

    override fun isForViewType(items: ArrayList<UiModel>, position: Int) = items[position] is UiFeedEvent

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(items: ArrayList<UiModel>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val item = items[position] as UiFeedEvent
        holder as ViewHolder
        holder.bind(item, position, listener)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_event)) {
        fun bind(item: UiFeedEvent, position: Int, listener: (position: Int) -> Unit) {
            with(itemView) {
                item_title.text = item.title.capitalize()
                item_lead.text = Html.fromHtml(item.lead.capitalize()).trim()
                item_place.text = item.place
                item_date.text = item.date
                item_month.text = item.month
                item_image.loadImg(item.image)
                item_premiere_tag.visibility = if(item.isPremiere) View.VISIBLE else View.GONE
                setOnClickListener { v -> listener(position) }
            }
        }
    }

}