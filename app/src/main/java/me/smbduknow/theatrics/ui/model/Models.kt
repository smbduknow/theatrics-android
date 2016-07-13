package me.smbduknow.theatrics.ui.model

import android.os.Parcel
import android.os.Parcelable
import me.smbduknow.theatrics.ui.commons.adapter.AdapterConst
import me.smbduknow.theatrics.ui.commons.adapter.ViewModel

data class UiEvent(
        val title: String,
        val type: String,
        val description: String
): ViewModel, Parcelable {

    override fun getViewType() = AdapterConst.ITEM_EVENT

    constructor(source: Parcel): this(
            source.readString(),
            source.readString(),
            source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeString(type)
        dest?.writeString(description)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UiEvent> = object : Parcelable.Creator<UiEvent> {
            override fun createFromParcel(source: Parcel): UiEvent = UiEvent(source)
            override fun newArray(size: Int): Array<UiEvent?> = arrayOfNulls(size)
        }
    }
}
