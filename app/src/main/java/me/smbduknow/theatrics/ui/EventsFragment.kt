package me.smbduknow.theatrics.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feed.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.mvp.ListMvpPresenter
import me.smbduknow.theatrics.mvp.ListMvpView
import me.smbduknow.theatrics.presenter.EventPresenter
import me.smbduknow.theatrics.presenter.ListState
import me.smbduknow.theatrics.ui.adapter.EventsAdapter
import me.smbduknow.theatrics.ui.commons.InfiniteScrollListener
import me.smbduknow.theatrics.ui.commons.SpaceItemDecorator
import me.smbduknow.theatrics.ui.commons.inflate
import me.smbduknow.theatrics.ui.model.UiEvent


class EventsFragment : MvpFragment<ListMvpPresenter, ListMvpView>(), ListMvpView {

    private val feedLoader by lazy { feed_loader }
    private val feedSwipeRefresh by lazy { feed_swipe_refresh }
    private val feedList by lazy { feed_list }
    private val feedEmpty by lazy { feed_empty }

    private var state = ListState(0, 0, 0)

    override fun onCreatePresenter(): ListMvpPresenter = EventPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_feed)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedSwipeRefresh.setOnRefreshListener { presenter?.requestNext(true) }

        val linearLayout = LinearLayoutManager(context)
        feedList.apply {
            setHasFixedSize(true)
            layoutManager = linearLayout
            addItemDecoration(SpaceItemDecorator(SpaceItemDecorator.VERTICAL))
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(linearLayout, { presenter?.requestNext() } ))
            adapter = EventsAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loaderManager.initLoader(101, null, this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("state", state.apply {
            listItems = (feedList.adapter as EventsAdapter).getItems()
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState == null) {
            state.listPage = 0
        } else {
            showFeed()
            state = savedInstanceState.getParcelable("state")
            addItems(state.listItems)
        }
    }


    override fun onResume() {
        super.onResume()
        presenter?.onViewAttached(this)
        if(state.state == 0) {
            presenter?.requestNext(true)
            state.state = 1
        }
    }

    override fun onPause() {
        presenter?.onViewDetached()
        super.onPause()
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

    override fun addItems(items: List<UiEvent>) {
        (feedList.adapter as EventsAdapter).addItems(items)
    }

    override fun clearItems() {
        (feedList.adapter as EventsAdapter).clear()
    }

}
