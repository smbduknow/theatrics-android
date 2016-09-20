package me.smbduknow.theatrics.ui.base

import rx.Subscription

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {

    protected var view: V? = null

    protected var subscription: Subscription? = null

    override fun onViewAttached(view: V) {
        this.view = view
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
    }

}