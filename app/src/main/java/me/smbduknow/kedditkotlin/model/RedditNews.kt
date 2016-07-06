package me.smbduknow.kedditkotlin.model

import me.smbduknow.kedditkotlin.AdapterConst
import me.smbduknow.kedditkotlin.ViewModel

data class RedditNews(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
): ViewModel {
    override fun getViewType() = AdapterConst.ITEM
}