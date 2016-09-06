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

}