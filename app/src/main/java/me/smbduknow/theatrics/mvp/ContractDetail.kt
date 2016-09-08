package me.smbduknow.theatrics.mvp

import me.smbduknow.theatrics.ui.model.UiFeedEvent

interface DetailMvpPresenter : MvpPresenter<DetailMvpView> {

    fun requestDetail(event: UiFeedEvent)
}

interface DetailMvpView : MvpView {

    fun showLoader(visible: Boolean = true)
    fun showDetail(visible: Boolean = true)
    fun showEmptyView(visible: Boolean = true)

    fun setTitle(title: String)
    fun setImage(image: String)

    fun setDetailedInfo(description: String,
                        tagline: String,
                        place: String,
                        address: String,
                        price: String,
                        date: String,
                        dateExtra: String,
                        runningTime: String
    )
}