package me.smbduknow.theatrics.mvp

import me.smbduknow.theatrics.ui.model.UiEvent

interface FeedMvpView : MvpView {

    fun showLoader(visible: Boolean = true)
    fun showFeed(visible: Boolean = true)
    fun showEmptyView(visible: Boolean = true)

    fun showPageLoader(visible: Boolean = true)

    fun addItems(items : List<UiEvent>)

}