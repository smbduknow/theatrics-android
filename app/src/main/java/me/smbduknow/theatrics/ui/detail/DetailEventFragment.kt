package me.smbduknow.theatrics.ui.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_detail.*
import me.smbduknow.mvpblueprint.BasePresenterFragment
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.misc.inflate
import me.smbduknow.theatrics.ui.model.UiDetailView
import me.smbduknow.theatrics.ui.model.UiFeedEvent
import me.smbduknow.theatrics.ui.model.ViewState

class DetailEventFragment : BasePresenterFragment<IDetailPresenter, IDetailView>(), IDetailView {

    override fun onCreatePresenter() = DetailEventPresenter()

    private val LAYOUT_RES = R.layout.fragment_detail

    private var state = UiDetailView(ViewState.STATE_LOADING)

    companion object {
        fun newInstance(args: Bundle): DetailEventFragment {
            val fragment = DetailEventFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(inflater, LAYOUT_RES)
    }

    override fun onPresenterReady() {
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
        detail_title.text = title
    }

    override fun setImage(image: String) {
        (activity as DetailActivity).setCollapsingImage(image)
    }

    override fun setDetailedInfo(tagline: String,
                                 leadText: String,
                                 description: String,
                                 place: String,
                                 address: String,
                                 price: String,
                                 date: String,
                                 dateExtra: String,
                                 runningTime: String) {
        detail_description.text = Html.fromHtml(description).trim('\n')
        setViewText(detail_lead_text, Html.fromHtml(leadText).trim('\n'))
        setViewText(detail_tagline_text, Html.fromHtml(tagline).trim('\n'))
        setViewText(detail_price, price)
        setViewText(detail_running_time, runningTime)
        setViewExtraText(detail_place, detail_place_address, place, address)
        setViewExtraText(detail_date, detail_date_time, date, dateExtra)
    }

    private fun setViewText(tv: TextView, s: CharSequence) {
        if(s.isEmpty()) {
            tv.visibility = View.GONE
        } else {
            tv.visibility = View.VISIBLE
            tv.text = s
        }
    }

    private fun setViewExtraText(tv: TextView, tvExtra: TextView, s: CharSequence, sExtra: CharSequence) {
        if(s.isEmpty()) {
            (tv.parent as View).visibility = View.GONE
        } else {
            (tv.parent as View).visibility = View.VISIBLE
            tv.text = s
            setViewText(tvExtra, sExtra)
        }
    }

}