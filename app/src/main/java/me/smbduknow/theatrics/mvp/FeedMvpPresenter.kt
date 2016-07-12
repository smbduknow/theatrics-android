package me.smbduknow.theatrics.mvp

interface FeedMvpPresenter : MvpPresenter {

    fun requestFeed(limit: Int, offset: Int)

}