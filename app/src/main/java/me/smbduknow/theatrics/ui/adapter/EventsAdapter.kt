package me.smbduknow.theatrics.ui.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.theatrics.ui.adapter.delegate.EventsDelegateAdapter
import me.smbduknow.theatrics.ui.commons.adapter.LoadingDelegateAdapter
import me.smbduknow.theatrics.ui.commons.adapter.ViewModel
import me.smbduknow.theatrics.ui.commons.adapter.ViewModelDelegateAdapter
import me.smbduknow.theatrics.ui.model.UiEvent
import java.util.*

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val ITEM_EVENT = 0
        val LOADING = 1
    }

    private var items: ArrayList<ViewModel>
    private var delegateAdapters = SparseArrayCompat<ViewModelDelegateAdapter>()

    private val loadingItem = object : ViewModel {
        override fun getViewType() = LOADING
    }

    init {
        delegateAdapters.put(LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ITEM_EVENT, EventsDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items.get(position).getViewType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }



    fun addItems(events: List<UiEvent>) {
        if(!items.isEmpty()) {
            val initPosition = items.size - 1
            items.removeAt(initPosition)
//            notifyItemRemoved(initPosition)
        }

        val lastPosition = getLastPosition()
        items.addAll(events)
        items.add(loadingItem)
//        notifyItemRangeChanged(lastPosition, items.size + 1 /* plus loading item */)
        notifyDataSetChanged()
    }

    fun clear() {
        if(!items.isEmpty()) {
            items.clear()
//            notifyItemRangeRemoved(0, getLastPosition())
            items.add(loadingItem)
//            notifyItemInserted(0)
            notifyDataSetChanged()
        }
    }

    fun getItems(): ArrayList<UiEvent> {
        return  items
                .filter { it.getViewType() == ITEM_EVENT }
                .map { it as UiEvent }
                as ArrayList<UiEvent>
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}

