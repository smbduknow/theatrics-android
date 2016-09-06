package me.smbduknow.theatrics.ui.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.theatrics.ui.adapter.delegate.EventsDelegateAdapter
import me.smbduknow.theatrics.ui.commons.adapter.LoadingDelegateAdapter
import me.smbduknow.theatrics.ui.commons.adapter.ViewModel
import me.smbduknow.theatrics.ui.commons.adapter.ViewModelDelegateAdapter
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import me.smbduknow.theatrics.ui.model.UiLoader
import java.util.*

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val ITEM_EVENT = 0
        val LOADING = 1
    }

    private var items: ArrayList<ViewModel>
    private var delegateAdapters = SparseArrayCompat<ViewModelDelegateAdapter>()

    private val loadingItem = UiLoader()

    init {
        delegateAdapters.put(LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ITEM_EVENT, EventsDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        val delegatesCount = delegateAdapters.size()
        for (i in 0..delegatesCount - 1) {
            val delegate = delegateAdapters.valueAt(i)
            if (delegate.isForViewType(items[position], position)) {
                return delegateAdapters.keyAt(i)
            }
        }

        throw NullPointerException(
                "No AdapterDelegate added that matches position=$position in data source")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position], position)
    }



    fun addItems(events: List<UiFeedEvent>) {
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

    fun getItems(): ArrayList<UiFeedEvent> {
        return items
                .filter { it is UiFeedEvent }
                .map { it as UiFeedEvent }
                as ArrayList<UiFeedEvent>
    }

    fun getItem(position: Int): ViewModel {
        return items[position]
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        (delegateAdapters.get(ITEM_EVENT) as EventsDelegateAdapter).listener = listener
    }

}

