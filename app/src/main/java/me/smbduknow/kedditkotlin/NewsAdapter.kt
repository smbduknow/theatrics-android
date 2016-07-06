package me.smbduknow.kedditkotlin

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.*

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM = 0
    val LOADING = 1

    private var items: ArrayList<ViewModel>
    private var delegateAdapters = SparseArrayCompat<ViewModelDelegateAdapter>()

    private val loadingItem = object : ViewModel {
        override fun getViewType() = LOADING
    }

    init {
        delegateAdapters.put(LOADING, LoadingDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

}

