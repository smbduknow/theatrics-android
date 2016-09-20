package me.smbduknow.theatrics.mvp

interface EventListMvpPresenter : ListMvpPresenter<EventListMvpView> {

    fun setParameters(
            location: String,
            date: String? = null
    )
}

interface EventListMvpView : ListMvpView