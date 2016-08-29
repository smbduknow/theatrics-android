package me.smbduknow.theatrics.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.mvp.DetailMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpView
import me.smbduknow.theatrics.mvp.MvpFragment
import me.smbduknow.theatrics.presenter.EventDetailPresenter
import me.smbduknow.theatrics.ui.activity.DetailActivity
import me.smbduknow.theatrics.ui.commons.inflate
import me.smbduknow.theatrics.ui.commons.loadImg
import me.smbduknow.theatrics.ui.model.UiDetailView
import me.smbduknow.theatrics.ui.model.UiEvent
import me.smbduknow.theatrics.ui.model.ViewState

class DetailEventFragment : MvpFragment<DetailMvpPresenter, DetailMvpView>(), DetailMvpView {

    private val mToolbar by lazy { detail_toolbar }
    private val mCollapsingToolbar by lazy { detail_collapsing_toolbar }

    private val mTitle by lazy { detail_title }
    private val mImage by lazy { detail_image }

    private var state = UiDetailView(ViewState.STATE_LOADING)

    override fun onCreatePresenter(): DetailMvpPresenter = EventDetailPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_detail)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailActivity).setToolbar(mToolbar)
    }

    override fun onResume() {
        super.onResume()
        presenter?.requestDetail(activity.intent.getSerializableExtra("event") as UiEvent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("state", state.apply {
            detailItem = null // TODO move to presenter
        })
    }

    override fun showLoader(visible: Boolean) {}

    override fun showDetail(visible: Boolean) {}

    override fun showEmptyView(visible: Boolean) {}

    override fun setTitle(title: String) {
        activity.title = title
        mCollapsingToolbar.title = title
        mTitle.text = title;
    }

    override fun setImage(image: String) {
        mImage.loadImg(image)
    }

    override fun setDescription(description: String) {}

}