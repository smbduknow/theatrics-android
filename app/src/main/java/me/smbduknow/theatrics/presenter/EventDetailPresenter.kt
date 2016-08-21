package me.smbduknow.theatrics.presenter

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import me.smbduknow.theatrics.mvp.DetailMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpView
import me.smbduknow.theatrics.mvp.ListMvpPresenter
import me.smbduknow.theatrics.mvp.ListMvpView
import me.smbduknow.theatrics.ui.model.UiEvent
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class EventDetailPresenter : DetailMvpPresenter {

    private var view: DetailMvpView? = null

    private var subscription: Subscription? = null

    override fun onViewAttached(view: DetailMvpView) {
        this.view = view
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
    }

    override fun requestDetail() {
        if(subscription != null) return

        view?.showLoader()

        subscription = ApiFactory.getApi().getEvent(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleError(it) },
                        { subscription = null }
                )

    }

    private fun handleResponse(response: ApiFeedItem) {
        view?.setTitle(response.title)
        view?.setImage(response.getTitleImage())
        view?.showDetail()
    }

    private fun handleError(error: Throwable) {
        view?.showEmptyView()
        if(BuildConfig.DEBUG) error.printStackTrace()
    }

}