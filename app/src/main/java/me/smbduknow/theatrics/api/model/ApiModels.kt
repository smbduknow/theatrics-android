package me.smbduknow.theatrics.api.model

import com.squareup.moshi.Json
import java.util.*

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

open class ApiFeedItem(id: Long, type: String) : ApiItem(id, type) {

    @Json(name = "name")
    var title: String = ""
    @Json(name = "full_name")
    var fullTitle: String = ""
    @Json(name = "lead")
    var leadText: String = ""
    @Json(name = "tagline")
    var tagline: String = ""

    var description: String = ""
    var images: List<ApiImage> = emptyList()

    var address: String = ""

    fun getTitleImage() = if (!images.isEmpty()) images[0].thumbnails.medium else ""
}

class ApiDetailItem(id: Long, type: String) : ApiFeedItem(id, type) {

    var price: ApiPrice? = null
    var place: ApiFeedItem? = null

    var dates: List<ApiDate> = emptyList()
}


class ApiDate(
        val start: String,
        val end: String
)

class ApiPrice(
        val lower: Int,
        val upper: Int,
        val text: String
)

class ApiImage(
        val image: String,
        val thumbnails: ApiThumbnails
) {
    class ApiThumbnails(
            @Json(name = "144x96") val small: String,
            @Json(name = "640x384") val medium: String
    )
}