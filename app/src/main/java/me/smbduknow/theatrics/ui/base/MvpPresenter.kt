package me.smbduknow.theatrics.ui.base

interface MvpPresenter<in V : MvpView> {

    fun onViewAttached(view: V)
    fun onViewDetached()

    fun onDestroy()

}