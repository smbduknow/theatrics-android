package me.smbduknow.theatrics.presenter

import android.os.Parcel
import android.os.Parcelable
import me.smbduknow.theatrics.ui.model.UiEvent

data class DetailState(
        var state: Int,
        var detailItem: UiEvent? = null
) : Parcelable {

    companion object {
        val STATE_LOADING = 0
        val STATE_CONTENT = 1
        val STATE_EMPTY = 2

        @JvmField val CREATOR: Parcelable.Creator<ListState> = object : Parcelable.Creator<ListState> {
            override fun createFromParcel(source: Parcel): ListState = ListState(source)
            override fun newArray(size: Int): Array<ListState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel): this(
            source.readInt(),
            source.readTypedObject(UiEvent.CREATOR))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(state)
        dest?.writeTypedObject(detailItem, flags)
    }



}