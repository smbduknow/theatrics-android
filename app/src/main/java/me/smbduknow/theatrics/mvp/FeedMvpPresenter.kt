package me.smbduknow.theatrics.mvp

interface FeedMvpPresenter : MvpPresenter {

    fun requestNext(refresh: Boolean = false)

}