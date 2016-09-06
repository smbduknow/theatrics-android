package me.smbduknow.theatrics.presenter

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import me.smbduknow.theatrics.mvp.BaseMvpPresenter
import me.smbduknow.theatrics.mvp.ListMvpPresenter
import me.smbduknow.theatrics.mvp.ListMvpView
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class EventListPresenter : BaseMvpPresenter<ListMvpView>(),ListMvpPresenter {

    private var isLoading = false

    private var page = 1

    override fun requestNext(refresh: Boolean) {
        if(isLoading) return

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
                        { handleError(it) }
                )
        isLoading = true
    }

    private fun handleResponse(response: ApiListResponse<ApiFeedItem>) {
        view?.addItems(response.items.map {
            UiFeedEvent(
                    it.id,
                    it.title,
                    it.type,
                    it.description,
                    it.getTitleImage())
        })
        view?.showFeed()
        page++

        isLoading = false
    }

    private fun handleError(error: Throwable) {
        view?.showEmptyView()
        if(BuildConfig.DEBUG) error.printStackTrace()

        isLoading = false
    }

}