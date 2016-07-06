package me.smbduknow.kedditkotlin

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.kedditkotlin.model.RedditNews
import java.util.*

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewModel>
    private var delegateAdapters = SparseArrayCompat<ViewModelDelegateAdapter>()

    private val loadingItem = object : ViewModel {
        override fun getViewType() = AdapterConst.LOADING
    }

    init {
        delegateAdapters.put(AdapterConst.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConst.ITEM, NewsDelegateAdapter())
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

    fun addNews(news: List<RedditNews>) {
        // first remove loading and notify
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        // insert news and the loading at the end of the list
        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item */)
    }

    fun clearAndAddNews(news: List<RedditNews>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getNews(): List<RedditNews> {
        return items
                .filter { it.getViewType() == AdapterConst.ITEM }
                .map { it as RedditNews }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}

