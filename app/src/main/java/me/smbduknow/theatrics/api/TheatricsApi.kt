package me.smbduknow.theatrics.api

import me.smbduknow.theatrics.api.model.ApiDetailItem
import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface TheatricsApi {

    @GET("/api/v1/events/?expand=place")
    fun getEvents(
            @Query("location")  location:   String,
            @Query("date")      date:       String? = null,
            @Query("page_size") limit:      Int = 20,
            @Query("page")      offset:     Int = 1
    ) : Observable<ApiListResponse<ApiFeedItem>>

    @GET("/api/v1/events/{id}/?expand=place")
    fun getEvent(
            @Path("id") id: Long
    ) : Observable<ApiDetailItem>


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