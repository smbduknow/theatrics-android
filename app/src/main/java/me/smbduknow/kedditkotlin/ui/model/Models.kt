package me.smbduknow.kedditkotlin.ui.model

import me.smbduknow.kedditkotlin.ui.commons.adapter.AdapterConst
import me.smbduknow.kedditkotlin.ui.commons.adapter.ViewModel
import java.io.Serializable

data class UiEvent(
        val title: String,
        val type: String,
        val description: String
): ViewModel {
    override fun getViewType() = AdapterConst.ITEM_EVENT
}
