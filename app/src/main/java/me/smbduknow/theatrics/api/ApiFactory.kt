package me.smbduknow.theatrics.api

import me.smbduknow.theatrics.api.model.ApiFeedItem
import me.smbduknow.theatrics.api.model.ApiListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable
import java.util.*

object ApiFactory {

    val BASE_URL = "http://theatrics.ru/"

    val CLIENT: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        CLIENT = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(CLIENT)
                .build()
    }

    fun getApi() : TheatricsApi = getRetrofit().create(TheatricsApi::class.java)



    // TODO use mockito
    fun getDummyApi() : TheatricsApi = object : TheatricsApi {

        override fun getEvent(id: Long): Observable<ApiFeedItem> {
            return Observable.just(ApiFeedItem(
                    id, "event",
                    "Dummy Event",
                    "Dummy Event the Play",
                    "Very nice Dummy Event for all ages",
                    "Description of Dummy Event\nIt may be very long but not this time... Sorry",
                    emptyList()
            ))
        }

        override fun getEvents(limit: Int, offset: Int): Observable<ApiListResponse<ApiFeedItem>> {
            return Observable.just(ApiListResponse(
                    "", 20, "", 40, Collections.nCopies(20,
                    ApiFeedItem(
                            1, "event",
                            "Dummy Event",
                            "Dummy Event the Play",
                            "Very nice Dummy Event for all ages",
                            "Description of Dummy Event\nIt may be very long but not this time... Sorry",
                            emptyList()
                    ))
            ))

        }

    }

}