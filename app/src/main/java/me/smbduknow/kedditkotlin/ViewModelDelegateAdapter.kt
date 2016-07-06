package me.smbduknow.kedditkotlin

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewModelDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel)
}