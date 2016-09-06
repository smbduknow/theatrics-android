package me.smbduknow.theatrics.mvp

import me.smbduknow.theatrics.ui.model.UiFeedEvent

interface ListMvpPresenter : MvpPresenter<ListMvpView> {

    fun requestNext(refresh: Boolean = false)
}

interface ListMvpView : MvpView {

    fun showLoader(visible: Boolean = true)
    fun showFeed(visible: Boolean = true)
    fun showEmptyView(visible: Boolean = true)

    fun showPageLoader(visible: Boolean = true)

    fun addItems(items : List<UiFeedEvent>)
    fun clearItems()
}