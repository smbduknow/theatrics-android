package me.smbduknow.theatrics.ui

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.theatrics.ui.commons.adapter.AdapterConst
import me.smbduknow.theatrics.ui.commons.adapter.LoadingDelegateAdapter
import me.smbduknow.theatrics.ui.commons.adapter.ViewModel
import me.smbduknow.theatrics.ui.commons.adapter.ViewModelDelegateAdapter
import me.smbduknow.theatrics.ui.model.UiEvent
import java.util.*

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewModel>
    private var delegateAdapters = SparseArrayCompat<ViewModelDelegateAdapter>()

    private val loadingItem = object : ViewModel {
        override fun getViewType() = AdapterConst.LOADING
    }

    init {
        delegateAdapters.put(AdapterConst.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConst.ITEM_EVENT, EventsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items.get(position).getViewType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }



    fun addNews(news: List<UiEvent>) {
        // first remove loading and notify
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        // insert news and the loading at the end of the list
        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item */)
    }

    fun clearAndAddNews(news: List<UiEvent>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getNews(): List<UiEvent> {
        return items
                .filter { it.getViewType() == AdapterConst.ITEM_EVENT }
                .map { it as UiEvent }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}

