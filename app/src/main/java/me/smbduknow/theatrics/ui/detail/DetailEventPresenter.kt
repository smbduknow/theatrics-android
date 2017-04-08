package me.smbduknow.theatrics.ui.detail

import me.smbduknow.mvpblueprint.BasePresenter
import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiDate
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiPrice
import me.smbduknow.theatrics.ui.misc.format
import me.smbduknow.theatrics.ui.model.UiDetailedEvent
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailEventPresenter : BasePresenter<IDetailView>(), IDetailPresenter {

    override fun requestDetail(event: UiFeedEvent) {
        view?.showLoader()
        ApiFactory.getApi().getEvent(event.id)
                .map { UiDetailedEvent(
                        id= it.id,
                        type= it.type,
                        title = it.title,
                        image = it.getTitleImage(),
                        lead = it.leadText ,
                        tagline = it.tagline ,
                        description = it.description ,
                        place = resolvePlaceName(it.place),
                        address = resolveAddress(it.place),
                        price = resolvePrice(it.price),
                        date = resolveDeteString(it.dates),
                        dateExtra = resolveDeteSubstring(it.dates),
                        runningTime = resolveRunningTime(it.dates) ,
                        isPremiere = it.isPremiere
                )}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleError(it) }
                )
    }

    private fun handleResponse(item: UiDetailedEvent?) {
        if(item != null) {
            view?.setTitle(item.title)
            view?.setImage(item.image)
            view?.setDetailedInfo(
                    item.tagline,
                    item.lead,
                    item.description,
                    item.place,
                    item.address,
                    item.price,
                    item.date,
                    item.dateExtra,
                    item.runningTime )
            view?.showDetail()
        }
    }

    private fun handleError(error: Throwable) {
        view?.showEmptyView()
        if(BuildConfig.DEBUG) error.printStackTrace()
    }


    private fun resolvePlaceName(placeItem: ApiFeedItem?): String {
        if(placeItem == null) return ""
        return if(placeItem.title.isNotEmpty())
            placeItem.title else placeItem.fullTitle
    }

    private fun resolveAddress(placeItem: ApiFeedItem?): String {
        if(placeItem == null) return ""
        return if(placeItem.address.isNotEmpty())
            placeItem.address else ""
    }


    private fun resolvePrice(price: ApiPrice?): String {
        if(price == null) return ""
        return price.text
    }

    // TODO common date parser
    private fun resolveDeteString(dates: List<ApiDate>): String {
        if(dates.isEmpty()) return ""
        return dates[0].start.format("d MMMM")
    }

    private fun resolveDeteSubstring(dates: List<ApiDate>): String {
        if(dates.isEmpty()) return ""
        val start = dates[0].start
        val end = dates[0].end
        return "%s - %s".format(start.format("EEEE, H:mm"), end.format("H:mm"))
    }

    private fun resolveRunningTime(dates: List<ApiDate>): String {
        if(dates.isEmpty()) return ""
        val start = dates[0].start
        val end = dates[0].end
        val minutes = (end.time - start.time) / (60 * 1000)
        return "%d:%02d".format(minutes / 60, minutes % 60)
    }

}