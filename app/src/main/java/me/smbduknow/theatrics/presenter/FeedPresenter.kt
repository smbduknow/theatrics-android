package me.smbduknow.theatrics.presenter

import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import me.smbduknow.theatrics.mvp.FeedMvpPresenter
import me.smbduknow.theatrics.mvp.FeedMvpView
import me.smbduknow.theatrics.ui.model.UiEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FeedPresenter(private val view: FeedMvpView) : FeedMvpPresenter {

    override fun requestFeed(limit: Int, offset: Int) {
        if(offset == 0) view.showLoader()
        ApiFactory.getApi().getEvents(limit, offset+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleError(it) }
                )
    }

    private fun handleResponse(response: ApiListResponse<ApiFeedItem>) {
        view.addItems(response.items.map {
            UiEvent(
                it.title,
                it.type,
                it.description )
        })
        view.showFeed()
    }

    private fun handleError(error: Throwable) {
        view.showEmptyView()
    }

}