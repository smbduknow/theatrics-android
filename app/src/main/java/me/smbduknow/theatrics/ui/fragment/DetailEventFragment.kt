package me.smbduknow.theatrics.ui.fragment

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.mvp.DetailMvpPresenter
import me.smbduknow.theatrics.mvp.DetailMvpView
import me.smbduknow.theatrics.mvp.MvpFragment
import me.smbduknow.theatrics.presenter.DetailState
import me.smbduknow.theatrics.presenter.EventDetailPresenter
import me.smbduknow.theatrics.ui.commons.inflate

class DetailEventFragment : MvpFragment<DetailMvpPresenter, DetailMvpView>(), DetailMvpView {

    private var state = DetailState(0)

    override fun onCreatePresenter(): DetailMvpPresenter = EventDetailPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.frament_detail_event)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loaderManager.initLoader(102, null, this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("state", state.apply {
            detailItem = null // TODO move to presenter
        })
    }

    override fun showLoader(visible: Boolean) {}

    override fun showDetail(visible: Boolean) {}

    override fun showEmptyView(visible: Boolean) {}

    override fun setTitle(title: String) {
        activity.title = title

    }

    override fun setImage(image: String) {}

    override fun setDescription(description: String) {}

}