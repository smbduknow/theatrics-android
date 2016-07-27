package me.smbduknow.theatrics.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feed.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.mvp.*
import me.smbduknow.theatrics.presenter.FeedPresenter
import me.smbduknow.theatrics.presenter.FeedState
import me.smbduknow.theatrics.ui.commons.InfiniteScrollListener
import me.smbduknow.theatrics.ui.commons.inflate
import me.smbduknow.theatrics.ui.model.UiEvent


abstract class MvpFragment<P : MvpPresenter<V>, V : MvpView> : Fragment(), MvpView, LoaderManager.LoaderCallbacks<P> {

    private var presenter: P? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loaderManager.initLoader(101, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<P> {
        return PresenterLoader(context, {  onCreatePresenter() })
    }

    override fun onLoadFinished(loader: Loader<P>, presenter: P) {
        if(this.presenter == null) this.presenter = presenter
    }

    override fun onLoaderReset(loader: Loader<P>) {
        presenter = null
    }

    abstract fun onCreatePresenter() : P


    override fun onResume() {
        super.onResume()
        presenter?.onViewAttached(this as V)
    }

    override fun onPause() {
        presenter?.onViewDetached()
        super.onPause()
    }

}