package me.smbduknow.theatrics.ui.commons.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewModelDelegateAdapter {

    fun isForViewType(item: ViewModel, position: Int): Boolean

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel, position: Int)
}