package me.smbduknow.theatrics.api.model

import com.squareup.moshi.Json
import java.io.Serializable

class ApiListResponse<T> (
        val previous : String,
        val took : Int,
        val next: String,
        val count : Int,
        val items : List<T>
)

open class ApiItem(
        val id: Long,
        val type: String
)

class ApiFeedItem(
        id: Long,
        type: String,
        @Json(name = "name") val title: String,
        @Json(name = "full_name") val fullTitle: String,
        @Json(name = "lead") val leadText: String,
        val description: String,
        val images: List<ApiImage>
) : ApiItem(id, type) {
    fun getTitleImage() = images[0].thumbnails.medium
}


class ApiImage(
        val image: String,
        val thumbnails: ApiThumbnails
) {
    class ApiThumbnails(
            @Json(name = "144x96") val small: String,
            @Json(name = "640x384") val medium: String
    )
}