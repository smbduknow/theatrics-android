package me.smbduknow.theatrics.mvp

interface ListMvpPresenter : MvpPresenter<ListMvpView> {

    fun requestNext(refresh: Boolean = false)

}