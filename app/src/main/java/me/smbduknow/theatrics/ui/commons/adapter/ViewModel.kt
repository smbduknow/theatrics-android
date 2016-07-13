package me.smbduknow.theatrics.ui.commons.adapter

import java.io.Serializable

interface ViewModel : Serializable {

    fun getViewType(): Int

}

