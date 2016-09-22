package me.smbduknow.theatrics.ui.model

import me.smbduknow.theatrics.ui.misc.adapter.ViewModel
import java.util.*


class UiLoader() : ViewModel


class UiFeedEvent(
        val id: Long,
        val type: String,
        val title: String,
        val lead: String,
        val place: String,
        val date: String,
        val month: String,
        val image: String,
        val isPremiere: Boolean
) : ViewModel


object ViewState {
    val STATE_LOADING = 0
    val STATE_CONTENT = 1
    val STATE_EMPTY = 2
}

class UiFeedView (
        var state: Int,
        var listPage: Int,
        var listItems: ArrayList<ViewModel> = ArrayList()
) : ViewModel


class UiDetailView(
        var state: Int,
        var detailItem: UiFeedEvent? = null
) : ViewModel

class UiCity(
        var slug: String,
        var title: String
) : ViewModel