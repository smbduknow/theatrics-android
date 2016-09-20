package me.smbduknow.theatrics.ui.misc.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.misc.inflate
import me.smbduknow.theatrics.ui.model.UiLoader

class LoadingDelegateAdapter : ViewModelDelegateAdapter {

    override fun isForViewType(item: ViewModel, position: Int) = item is UiLoader

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel, position: Int) {}

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}