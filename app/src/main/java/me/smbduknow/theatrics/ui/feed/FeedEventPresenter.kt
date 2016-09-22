package me.smbduknow.theatrics.ui.feed

import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import me.smbduknow.theatrics.ui.base.BasePresenter
import me.smbduknow.theatrics.ui.misc.format
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import me.smbduknow.theatrics.ui.model.UiLoader
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class FeedEventPresenter : BasePresenter<IFeedView>(), IFeedPresenter {

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    private var isLoading = false

    private var page = 1

    private var pLocation = "spb"
    private var pDate: String? = null

    override fun setParameters(location: String, date: String?) {
        pLocation = location
        pDate = date
    }

    override fun requestNext(refresh: Boolean) {
        if(isLoading) return

        if(refresh) {
            view?.showLoader()
            view?.clearItems()
            page = 1
        }
        subscription = ApiFactory.getApi().getEvents(pLocation, pDate, 10, page)
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
            val date = sdf.parse(it.dates[0].start)
            UiFeedEvent(
                    it.id,
                    it.type,
                    it.getTitleString(),
                    it.leadText,
                    it.place?.getTitleString() ?: "TBA",
                    date.format("dd"),
                    date.format("LLLL").substring(0..2),
                    it.getTitleImage(),
                    it.isPremiere)
        })
        if(response.next.isNotEmpty()) view?.addItems(listOf(UiLoader()))
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