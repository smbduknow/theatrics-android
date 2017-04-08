package me.smbduknow.theatrics.ui.detail

import me.smbduknow.mvpblueprint.mvp.MvpPresenter
import me.smbduknow.mvpblueprint.mvp.MvpView
import me.smbduknow.theatrics.ui.model.UiFeedEvent

interface IDetailPresenter : MvpPresenter<IDetailView> {

    fun requestDetail(event: UiFeedEvent)
}

interface IDetailView : MvpView {

    fun showLoader(visible: Boolean = true)
    fun showDetail(visible: Boolean = true)
    fun showEmptyView(visible: Boolean = true)

    fun setTitle(title: String)
    fun setImage(image: String)

    fun setDetailedInfo(tagline: String,
                        leadText: String,
                        description: String,
                        place: String,
                        address: String,
                        price: String,
                        date: String,
                        dateExtra: String,
                        runningTime: String
    )
}