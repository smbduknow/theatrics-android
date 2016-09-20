package me.smbduknow.theatrics.ui.feed.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.theatrics.ui.feed.adapter.delegate.EventsDelegateAdapter
import me.smbduknow.theatrics.ui.misc.adapter.LoadingDelegateAdapter
import me.smbduknow.theatrics.ui.misc.adapter.ViewModel
import me.smbduknow.theatrics.ui.misc.adapter.ViewModelDelegateAdapter
import java.util.*

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val ITEM_EVENT = 0
        val LOADING = 1
    }

    private var items: ArrayList<ViewModel>
    private var delegateAdapters = SparseArrayCompat<ViewModelDelegateAdapter>()

    init {
        delegateAdapters.put(LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ITEM_EVENT, EventsDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        val delegatesCount = delegateAdapters.size()
        for (i in 0..delegatesCount - 1) {
            if (delegateAdapters.valueAt(i).isForViewType(items[position], position)) {
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

    fun isEmpty() = items.isEmpty()

    fun clear() = items.clear()
    fun remove(position: Int) = items.removeAt(position)

    fun add(event: ViewModel) = items.add(event)
    fun addAll(events: List<ViewModel>) = items.addAll(events)

    fun getItem(position: Int) = items[position]
    fun getItems() = items

    inline fun <reified T> getItemsByType() = getItems()
            .filter { it is T }
            .map { it as T }
            as ArrayList<T>



    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        (delegateAdapters.get(ITEM_EVENT) as EventsDelegateAdapter).listener = listener
    }

}

