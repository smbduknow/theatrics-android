package me.smbduknow.theatrics.mvp

interface DetailMvpPresenter : MvpPresenter<DetailMvpView> {

    fun requestDetail()
}

interface DetailMvpView : MvpView {

    fun showLoader(visible: Boolean = true)
    fun showDetail(visible: Boolean = true)
    fun showEmptyView(visible: Boolean = true)

    fun setTitle(title: String)
    fun setImage(image: String)

    fun setDescription(description: String)
}