package me.smbduknow.theatrics.ui.feed

import me.smbduknow.mvpblueprint.mvp.MvpPresenter
import me.smbduknow.mvpblueprint.mvp.MvpView
import me.smbduknow.theatrics.ui.misc.adapter.ViewModel

interface IFeedPresenter : MvpPresenter<IFeedView> {

    fun setParameters(
            location: String,
            date: String? = null
    )

    fun requestNext(refresh: Boolean = false)
}

interface IFeedView : MvpView {

    fun showLoader(visible: Boolean = true)
    fun showFeed(visible: Boolean = true)
    fun showEmptyView(visible: Boolean = true)

    fun showPageLoader(visible: Boolean = true)

    fun addItems(items : List<ViewModel>)
    fun clearItems()
}