package me.smbduknow.theatrics.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feed.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.mvp.FeedMvpView
import me.smbduknow.theatrics.presenter.FeedPresenter
import me.smbduknow.theatrics.presenter.FeedPresenterLoader
import me.smbduknow.theatrics.presenter.FeedState
import me.smbduknow.theatrics.ui.commons.InfiniteScrollListener
import me.smbduknow.theatrics.ui.commons.inflate
import me.smbduknow.theatrics.ui.model.UiEvent


class EventsFragment : Fragment(), FeedMvpView, LoaderManager.LoaderCallbacks<FeedPresenter> {

    private val feedLoader by lazy { feed_loader }
    private val feedList by lazy { feed_list }
    private val feedEmpty by lazy { feed_empty }

    private var presenter: FeedPresenter? = null

    private var state = FeedState(0, 0, 0)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_feed)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = LinearLayoutManager(context)
        feedList.apply {
            setHasFixedSize(true)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(linearLayout, {
                Snackbar.make(view as View, "${state.listPage}" as CharSequence, Snackbar.LENGTH_SHORT).show()
                presenter?.requestNext()
            } ))
            adapter = EventsAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            state.listPage = 0
        } else {
            showFeed()
            state = savedInstanceState.getParcelable("state")
            addItems(state.listItems)
        }

//      LoaderCallbacks as an object, so no hint regarding loader will be leak to the subclasses.
        loaderManager.initLoader(101, null, this)
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<FeedPresenter> {
        return FeedPresenterLoader(context)
    }

    override fun onLoadFinished(loader: Loader<FeedPresenter>, presenter: FeedPresenter) {
        if (this@EventsFragment.presenter == null) {
            this@EventsFragment.presenter = presenter
        }
    }

    override fun onLoaderReset(loader: Loader<FeedPresenter>) {
        this@EventsFragment.presenter = null
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("state", state.apply {
            listItems = (feedList.adapter as EventsAdapter).getNews()
            listPosition = (feedList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun showLoader(visible: Boolean) {
        feedLoader.visibility = View.VISIBLE
        feedList.visibility = View.GONE
        feedEmpty.visibility = View.GONE
    }

    override fun showFeed(visible: Boolean) {
        feedLoader.visibility = View.GONE
        feedList.visibility = View.VISIBLE
        feedEmpty.visibility = View.GONE
    }

    override fun showEmptyView(visible: Boolean) {
        feedLoader.visibility = View.GONE
        feedList.visibility = View.GONE
        feedEmpty.visibility = View.VISIBLE
    }

    override fun showPageLoader(visible: Boolean) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun addItems(items: List<UiEvent>) {
        (feedList.adapter as EventsAdapter).addNews(items)
    }

}
