package me.smbduknow.theatrics.ui.base

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

abstract class BaseAdapter<T>(
        delegates: Map<Int, AdapterDelegate<ArrayList<T>>>
) : ListDelegationAdapter<ArrayList<T>>() {

    init {
        delegates.forEach { delegatesManager.addDelegate(it.key, it.value) }
        items = ArrayList()
    }

    fun isEmpty() = items.isEmpty()

    fun clear() = items.clear()
    fun remove(position: Int) = items.removeAt(position)
    fun remove(item: T) = items.remove(item)

    fun add(item: T) = items.add(item)
    fun add(pos: Int, item: T) = items.add(pos, item)
    fun addAll(items: List<T>) = this.items.addAll(items)

    fun replace(pos: Int, item: T) = items.set(pos, item)

    fun getItem(position: Int) = items[position]
    fun getItemOrNull(position: Int) = items.getOrNull(position)

    inline fun <reified T> getItemsByType() = getItems()
            .filter { it is T }
            .map { it as T }
            as ArrayList<T>

}
