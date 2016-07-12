package me.smbduknow.theatrics.api.model

import com.squareup.moshi.Json

class ApiListResponse<T> (
        val previous : String,
        val took : Int,
        val next: String,
        val count : Int,
        val items : List<T>
)

class ApiFeedItem(
        @Json(name = "full_name") val title: String,
        val type: String,
        val description: String
)


