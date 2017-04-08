package com.urworld.android.ui.misc.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.misc.inflate
import me.smbduknow.theatrics.ui.model.UiLoader
import me.smbduknow.theatrics.ui.model.UiModel

class LoaderDelegateAdapter : AdapterDelegate<ArrayList<UiModel>>() {

    private val RES_ID = R.layout.item_loading

    override fun isForViewType(items: ArrayList<UiModel>, position: Int)
            = items[position] is UiLoader

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent.inflate(RES_ID, false))

    override fun onBindViewHolder(items: ArrayList<UiModel>, pos: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) { }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}