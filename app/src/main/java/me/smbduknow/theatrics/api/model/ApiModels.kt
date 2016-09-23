package me.smbduknow.theatrics.api.model

import com.google.gson.annotations.SerializedName
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

    @SerializedName("name")
    var title: String = ""
    @SerializedName("full_name")
    var fullTitle: String = ""
    @SerializedName("lead")
    var leadText: String = ""
    @SerializedName("tagline")
    var tagline: String = ""
    @SerializedName("is_premiere")
    var isPremiere: Boolean = false

    var description: String = ""
    var address: String = ""

    var images: List<ApiImage> = emptyList()
    var dates: List<ApiDate> = emptyList()

    var place: ApiFeedItem? = null

    fun getTitleString() = if(title.isNotEmpty()) title else fullTitle
    fun getTitleImage() = if (!images.isEmpty()) images[0].thumbnails.medium else ""
}

class ApiDetailItem(id: Long, type: String) : ApiFeedItem(id, type) {

    var price: ApiPrice? = null

}


class ApiDate(
        val start: Date,
        val end: Date
)

class ApiPrice(
        val lower: Int?,
        val upper: Int?,
        val text: String
)

class ApiImage(
        val image: String,
        val thumbnails: ApiThumbnails
) {
    class ApiThumbnails(
            @SerializedName("144x96") val small: String,
            @SerializedName("640x384") val medium: String
    )
}