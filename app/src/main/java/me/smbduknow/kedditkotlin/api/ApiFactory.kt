package me.smbduknow.kedditkotlin.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    val BASE_URL = "http://theatrics.ru/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    fun getApiService() : TheatricsApi = getRetrofit().create(TheatricsApi::class.java)

}