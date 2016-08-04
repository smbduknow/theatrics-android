package me.smbduknow.theatrics.presenter

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import me.smbduknow.theatrics.mvp.ListMvpPresenter
import me.smbduknow.theatrics.mvp.ListMvpView
import me.smbduknow.theatrics.ui.model.UiEvent
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class EventPresenter : ListMvpPresenter {

    private var view: ListMvpView? = null

    private var subscription: Subscription? = null

    private var page = 1

    override fun onViewAttached(view: ListMvpView) {
        this.view = view
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
    }

    override fun requestNext(refresh: Boolean) {
        if(subscription != null) return

        if(refresh) {
            view?.showLoader()
            view?.clearItems()
            page = 1
        }
        subscription = ApiFactory.getApi().getEvents(10, page)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleError(it) },
                        { subscription = null }
                )

    }

    private fun handleResponse(response: ApiListResponse<ApiFeedItem>) {
        view?.addItems(response.items.map {
            UiEvent(
                    it.title,
                    it.type,
                    it.description,
                    it.getTitleImage())
        })
        view?.showFeed()
        page++
    }

    private fun handleError(error: Throwable) {
        view?.showEmptyView()
        if(BuildConfig.DEBUG) error.printStackTrace()
    }

}