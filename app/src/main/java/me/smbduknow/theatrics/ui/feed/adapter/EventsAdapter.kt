package me.smbduknow.theatrics.ui.feed.adapter

import com.urworld.android.ui.misc.adapter.LoaderDelegateAdapter
import me.smbduknow.theatrics.ui.base.BaseAdapter
import me.smbduknow.theatrics.ui.feed.adapter.delegate.EventItemAdapterDelegate
import me.smbduknow.theatrics.ui.model.UiModel

class EventsAdapter : BaseAdapter<UiModel>( mapOf(
        TYPE_LOADER to LoaderDelegateAdapter(),
        TYPE_EVENT to EventItemAdapterDelegate()
) ) {

    companion object {
        val TYPE_LOADER = 0
        val TYPE_EVENT = 1
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        (delegatesManager.getDelegateForViewType(TYPE_EVENT) as EventItemAdapterDelegate).listener = listener
    }

}
