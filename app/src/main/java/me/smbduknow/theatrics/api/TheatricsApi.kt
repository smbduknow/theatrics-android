package me.smbduknow.theatrics.api

import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface TheatricsApi {

    @GET("/api/v1/events/")
    fun getEvents(
            @Query("page_size") limit: Int,
            @Query("page") offset: Int
    ) : Observable<ApiListResponse<ApiFeedItem>>

    @GET("/api/v1/events/{id}/")
    fun getEvent(
            @Path("id") id: Long
    ) : Observable<ApiFeedItem>


//    @GET("/api/v1/places/")
//    fun getPlaces() : Observable<ApiListResponse<ApiFeedItem>>
//
//    @GET("/api/v1/places/{id}/")
//    fun getPlace(
//            @Path("id") id: Int) : Observable<Object>
//
//
//    @GET("/api/v1/search/")
//    fun search(
//            @Query("q") q: String) : Observable<ApiListResponse<ApiFeedItem>>

}