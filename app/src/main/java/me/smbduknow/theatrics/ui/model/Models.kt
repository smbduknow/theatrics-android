package me.smbduknow.theatrics.ui.model

import me.smbduknow.theatrics.ui.commons.adapter.ViewModel
import java.util.*


class UiLoader() : ViewModel


class UiFeedEvent(
        val id: Long,
        val title: String,
        val type: String,
        val place: String,
        val date: String,
        val month: String,
        val image: String
) : ViewModel


object ViewState {
    val STATE_LOADING = 0
    val STATE_CONTENT = 1
    val STATE_EMPTY = 2
}

class UiFeedView (
        var state: Int,
        var listPage: Int,
        var listItems: ArrayList<UiFeedEvent> = ArrayList()
) : ViewModel


class UiDetailView(
        var state: Int,
        var detailItem: UiFeedEvent? = null
) : ViewModel