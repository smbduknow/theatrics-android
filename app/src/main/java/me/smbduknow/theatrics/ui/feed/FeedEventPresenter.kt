package me.smbduknow.theatrics.ui.feed

import me.smbduknow.mvpblueprint.BasePresenter
import me.smbduknow.theatrics.BuildConfig
import me.smbduknow.theatrics.api.ApiFactory
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import me.smbduknow.theatrics.ui.misc.format
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import me.smbduknow.theatrics.ui.model.UiLoader
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FeedEventPresenter : BasePresenter<IFeedView>(), IFeedPresenter {

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
        ApiFactory.getApi().getEvents(pLocation, pDate, 10, page)
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
                    it.type,
                    it.getTitleString(),
                    it.getTitleImage(),
                    it.leadText,
                    it.place?.getTitleString() ?: "TBA",
                    it.dates[0].start.format("dd"),
                    it.dates[0].start.format("LLLL").substring(0..2),
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