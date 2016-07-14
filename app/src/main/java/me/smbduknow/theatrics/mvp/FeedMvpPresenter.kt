package me.smbduknow.theatrics.mvp

interface FeedMvpPresenter : MvpPresenter<FeedMvpView> {

    fun requestNext(refresh: Boolean = false)

}