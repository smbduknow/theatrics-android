package me.smbduknow.theatrics.ui.model

import java.io.Serializable
import java.util.*

interface UiModel : Serializable

class UiLoader : UiModel


data class UiFeedEvent(
        val id: Long,
        val type: String,
        val title: String,
        val image: String,
        val lead: String,
        val place: String,
        val date: String,
        val month: String,
        val isPremiere: Boolean
) : UiModel

data class UiDetailedEvent(
        val id: Long,
        val type: String,
        val title: String,
        val image: String,
        val lead: String,
        val tagline: String,
        val description: String,
        val place: String,
        val address: String,
        val price: String,
        val date: String,
        val dateExtra: String,
        val runningTime: String,
        val isPremiere: Boolean
) : UiModel


object ViewState {
    val STATE_LOADING = 0
    val STATE_CONTENT = 1
    val STATE_EMPTY = 2
}

class UiFeedView (
        var state: Int,
        var listPage: Int,
        var listItems: ArrayList<UiModel> = ArrayList()
) : UiModel


class UiDetailView(
        var state: Int,
        var detailItem: UiFeedEvent? = null
) : UiModel

class UiCity(
        var slug: String,
        var title: String
) : UiModel