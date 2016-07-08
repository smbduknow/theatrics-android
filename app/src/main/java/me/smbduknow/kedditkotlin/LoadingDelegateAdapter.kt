package me.smbduknow.kedditkotlin

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.smbduknow.kedditkotlin.commons.inflate

class LoadingDelegateAdapter : ViewModelDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel) {
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}