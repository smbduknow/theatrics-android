package me.smbduknow.kedditkotlin.ui.commons.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

public interface ViewModelDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel)
}