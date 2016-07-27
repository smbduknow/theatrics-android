package me.smbduknow.theatrics.presenter

import android.os.Parcel
import android.os.Parcelable
import me.smbduknow.theatrics.ui.model.UiEvent

data class FeedState(
        var state: Int,
        var listPage: Int,
        var listPosition: Int,
        var listItems: List<UiEvent> = emptyList()
) : Parcelable {

    companion object {
        val STATE_LOADING = 0
        val STATE_CONTENT = 1
        val STATE_EMPTY = 2

        @JvmField val CREATOR: Parcelable.Creator<FeedState> = object : Parcelable.Creator<FeedState> {
            override fun createFromParcel(source: Parcel): FeedState = FeedState(source)
            override fun newArray(size: Int): Array<FeedState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel): this(
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.createTypedArrayList(UiEvent.CREATOR))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(state)
        dest?.writeInt(listPage)
        dest?.writeInt(listPosition)
        dest?.writeTypedList(listItems)
    }



}