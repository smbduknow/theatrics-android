package me.smbduknow.theatrics.presenter

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.mvp.BaseMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpView
import me.smbduknow.theatrics.ui.model.UiEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class EventDetailPresenter : BaseMvpPresenter<DetailMvpView>(), DetailMvpPresenter {

    override fun requestDetail(event: UiEvent) {
        view?.showLoader()
        subscription = ApiFactory.getApi().getEvent(event.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleError(it) }
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