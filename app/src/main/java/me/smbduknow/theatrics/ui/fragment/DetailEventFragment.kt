package me.smbduknow.theatrics.ui.fragment

import android.os.Bundle
import android.text.Html
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
import me.smbduknow.theatrics.ui.model.UiDetailView
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import me.smbduknow.theatrics.ui.model.ViewState

class DetailEventFragment : MvpFragment<DetailMvpPresenter, DetailMvpView>(), DetailMvpView {

    private val mTitle by lazy { detail_title }
    private val mDescription by lazy { detail_description}
    private val mPlace by lazy { detail_place }
    private val mRunningTime by lazy { detail_running_time }

    private var state = UiDetailView(ViewState.STATE_LOADING)

    companion object {
        fun newInstance(args: Bundle): DetailEventFragment {
            val fragment = DetailEventFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreatePresenter(): DetailMvpPresenter = EventDetailPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_detail)
    }

    override fun onResume() {
        super.onResume()
        presenter?.requestDetail(arguments.getSerializable("event") as UiFeedEvent)
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
        mTitle.text = title
    }

    override fun setImage(image: String) {
        (activity as DetailActivity).setCollapsingImage(image)
    }

    override fun setDescription(description: String,
                                place: String,
                                runningTime: String) {
        mDescription.text = Html.fromHtml(description).trim('\n')
        mPlace.text = place
        mRunningTime.text = runningTime
    }

}