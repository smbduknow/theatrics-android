package me.smbduknow.theatrics.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import me.smbduknow.theatrics.mvp.MvpPresenter
import me.smbduknow.theatrics.mvp.MvpView
import me.smbduknow.theatrics.mvp.PresenterLoader


abstract class MvpFragment<P : MvpPresenter<V>, V : MvpView> : Fragment(), MvpView, LoaderManager.LoaderCallbacks<P> {

    protected var presenter: P? = null

    abstract fun onCreatePresenter() : P

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

    override fun onResume() {
        super.onResume()
        presenter?.onViewAttached(this as V)
    }

    override fun onPause() {
        presenter?.onViewDetached()
        super.onPause()
    }

}