package me.smbduknow.theatrics.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    val BASE_URL = "http://theatrics.ru/"
    val BASE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    val CLIENT: OkHttpClient

    val GSON: Gson = GsonBuilder().setDateFormat(BASE_DATE_FORMAT).create()

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
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .client(CLIENT)
                .build()
    }

    fun getApi() : TheatricsApi = getRetrofit().create(TheatricsApi::class.java)

}