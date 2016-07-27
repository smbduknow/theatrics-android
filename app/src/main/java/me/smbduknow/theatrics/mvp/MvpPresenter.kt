package me.smbduknow.theatrics.mvp

interface MvpPresenter<in V : MvpView> {

    fun onViewAttached(view: V)
    fun onViewDetached()

    fun onDestroy()

}