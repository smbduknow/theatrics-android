package me.smbduknow.kedditkotlin.ui

import me.smbduknow.kedditkotlin.api.ApiFactory
import me.smbduknow.kedditkotlin.api.TheatricsApi
import rx.Observable

class EventsManager() {

    private val api: TheatricsApi = ApiFactory.getApiService()

    fun getEvents(): Observable<List<Object>> {
        return api.getEvents()
    }

}