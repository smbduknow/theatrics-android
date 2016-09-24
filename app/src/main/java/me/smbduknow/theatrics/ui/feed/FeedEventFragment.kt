package me.smbduknow.theatrics.ui.feed

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feed.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.preference.PreferenceHelper
import me.smbduknow.theatrics.ui.base.BasePresenterFragment
import me.smbduknow.theatrics.ui.base.PresenterFactory
import me.smbduknow.theatrics.ui.detail.DetailActivity
import me.smbduknow.theatrics.ui.feed.adapter.EventsAdapter
import me.smbduknow.theatrics.ui.misc.InfiniteScrollListener
import me.smbduknow.theatrics.ui.misc.SpaceItemDecorator
import me.smbduknow.theatrics.ui.misc.adapter.ViewModel
import me.smbduknow.theatrics.ui.misc.format
import me.smbduknow.theatrics.ui.misc.inflate
import me.smbduknow.theatrics.ui.model.UiFeedView
import me.smbduknow.theatrics.ui.model.UiLoader
import me.smbduknow.theatrics.ui.model.ViewState
import java.util.*

class FeedEventFragment : BasePresenterFragment<IFeedPresenter, IFeedView>(), IFeedView {

    private val LAYOUT_RES = R.layout.fragment_feed

    private val feedLoader          by lazy { feed_loader }
    private val feedSwipeRefresh    by lazy { feed_swipe_refresh }
    private val feedList            by lazy { feed_list }
    private val feedEmpty           by lazy { feed_empty }

    private var feedAdapter: EventsAdapter? = null

    private var state = UiFeedView(ViewState.STATE_LOADING, 0)

    var date: Date? = null

    override fun onPresenterFactoryCreated() = object: PresenterFactory<IFeedPresenter> {
        override fun create() = FeedEventPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(inflater, LAYOUT_RES)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedSwipeRefresh.setOnRefreshListener { refreshFeed() }

        val linearLayout = LinearLayoutManager(context)
        feedAdapter = EventsAdapter()
        feedAdapter?.setOnItemClickListener { position -> startDetailActivity(position) }
        feedList.apply {
            setHasFixedSize(true)
            layoutManager = linearLayout
            addItemDecoration(SpaceItemDecorator(SpaceItemDecorator.Companion.VERTICAL))
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(linearLayout, { loadNextPage() } ))
            adapter = feedAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("state", state.apply {
            listItems = feedAdapter?.getItems() ?: listItems
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState == null) {
            state.listPage = 0
        } else {
            showFeed()
            state = savedInstanceState.getSerializable("state") as UiFeedView
            addItems(state.listItems)
        }
    }


    override fun onResume() {
        super.onResume()
        if(state.state == ViewState.STATE_LOADING) {
            refreshFeed()
            state.state = ViewState.STATE_CONTENT
        }
    }



    override fun showLoader(visible: Boolean) {
        feedSwipeRefresh.isRefreshing = true
        feedLoader.visibility = View.VISIBLE
        feedList.visibility = View.GONE
        feedEmpty.visibility = View.GONE
    }

    override fun showFeed(visible: Boolean) {
        feedSwipeRefresh.isRefreshing = false
        feedLoader.visibility = View.GONE
        feedList.visibility = View.VISIBLE
        feedEmpty.visibility = View.GONE
    }

    override fun showEmptyView(visible: Boolean) {
        feedSwipeRefresh.isRefreshing = false
        feedLoader.visibility = View.GONE
        feedList.visibility = View.GONE
        feedEmpty.visibility = View.VISIBLE
    }

    override fun showPageLoader(visible: Boolean) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun addItems(items: List<ViewModel>) {
        if(feedAdapter?.getItems()?.isNotEmpty() ?: false ) {
            val loader = feedAdapter?.getItems()?.last() ?: null
            if(loader != null && loader is UiLoader) feedAdapter?.remove(feedAdapter!!.itemCount - 1)
        }
        feedAdapter?.addAll(items)
        feedAdapter?.notifyDataSetChanged()
    }

    override fun clearItems() {
        feedAdapter?.clear()
        feedAdapter?.notifyDataSetChanged()
    }

    fun refreshFeed() {
        val slug = PreferenceHelper.getUserCity()
        presenter?.setParameters(if (slug.isNotEmpty()) slug else "msk", date?.format("yyyy-MM-dd"))
        presenter?.requestNext(true)
    }
    fun loadNextPage() = presenter?.requestNext()

    fun startDetailActivity(position: Int) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("event", feedAdapter?.getItem(position))
        startActivity(intent)
    }
}


