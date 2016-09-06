package me.smbduknow.theatrics.presenter

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiDetailItem
import me.smbduknow.theatrics.mvp.BaseMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpView
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat

class EventDetailPresenter : BaseMvpPresenter<DetailMvpView>(), DetailMvpPresenter {

    override fun requestDetail(event: UiFeedEvent) {
        view?.showLoader()
        subscription = ApiFactory.getApi().getEvent(event.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleError(it) }
                )
    }

    private fun handleResponse(item: ApiDetailItem) {
        var runningTime = ""
        if(item.dates.isNotEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val start = sdf.parse(item.dates[0].start)
            val end = sdf.parse(item.dates[0].end)
            val millis = end.time - start.time
            runningTime = SimpleDateFormat("hh:mm").format(millis)
        }

        var place = ""
        if(item.place != null) {
            if(item.place!!.title.isNotEmpty())
                place = item.place!!.title
            else
                place = item.place!!.fullTitle
        }

        view?.setTitle(item.title)
        view?.setImage(item.getTitleImage())
        view?.setDescription(item.description, place, runningTime )
        view?.showDetail()
    }

    private fun handleError(error: Throwable) {
        view?.showEmptyView()
        if(BuildConfig.DEBUG) error.printStackTrace()
    }

}