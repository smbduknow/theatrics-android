package me.smbduknow.theatrics.ui.commons.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.commons.inflate

class LoadingDelegateAdapter : ViewModelDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel, position: Int) {}

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}