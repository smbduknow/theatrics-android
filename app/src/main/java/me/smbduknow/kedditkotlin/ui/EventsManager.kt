package me.smbduknow.kedditkotlin.ui

import me.smbduknow.kedditkotlin.api.ApiFactory
import me.smbduknow.kedditkotlin.api.TheatricsApi
import me.smbduknow.kedditkotlin.api.model.ApiFeedItem
import me.smbduknow.kedditkotlin.api.model.ApiListResponse
import me.smbduknow.kedditkotlin.ui.model.UiEvent
import rx.Observable

class EventsManager() {

    private val api: TheatricsApi = ApiFactory.getApiService()

    fun getEvents(): Observable<ApiListResponse<ApiFeedItem>> {
        return api.getEvents()
    }

}