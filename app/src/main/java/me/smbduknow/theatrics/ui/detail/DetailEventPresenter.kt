package me.smbduknow.theatrics.ui.detail

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiDate
import me.smbduknow.theatrics.api.model.ApiDetailItem
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiPrice
import me.smbduknow.theatrics.ui.base.BasePresenter
import me.smbduknow.theatrics.ui.misc.format
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class DetailEventPresenter : BasePresenter<IDetailView>(), IDetailPresenter {

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

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
        view?.setTitle(item.title)
        view?.setImage(item.getTitleImage())
        view?.setDetailedInfo(
                item.tagline,
                item.leadText,
                item.description,
                resolvePlaceName(item.place),
                resolveAddress(item.place),
                resolvePrice(item.price),
                resolveDeteString(item.dates),
                resolveDeteSubstring(item.dates),
                resolveRunningTime(item.dates) )
        view?.showDetail()
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
        val date = sdf.parse(dates[0].start)
        return date.format("d MMMM")
    }

    private fun resolveDeteSubstring(dates: List<ApiDate>): String {
        if(dates.isEmpty()) return ""
        val start = sdf.parse(dates[0].start)
        val end = sdf.parse(dates[0].end)
        return "%s - %s".format(start.format("EEEE, H:mm"), end.format("H:mm"))
    }

    private fun resolveRunningTime(dates: List<ApiDate>): String {
        if(dates.isEmpty()) return ""
        val start = sdf.parse(dates[0].start)
        val end = sdf.parse(dates[0].end)
        val minutes = (end.time - start.time) / (60 * 1000)
        return "%d:%02d".format(minutes / 60, minutes % 60)
    }

}