package me.smbduknow.theatrics.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader


abstract class BasePresenterFragment<P : MvpPresenter<V>, V : MvpView> : BaseFragment(),
        LoaderManager.LoaderCallbacks<P> {

    private val LOADER_ID = 1

    protected var presenter: P? = null

    abstract fun onPresenterFactoryCreated() : PresenterFactory<P>


    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loaderManager.initLoader(LOADER_ID, null, this)
    }

    @CallSuper
    override  fun onDestroyView() {
        presenter?.onViewDetached()
        super.onDestroyView()
    }


    protected fun getMvpView(): V {
        @Suppress("UNCHECKED_CAST")
        return this as V
    }


    // LoaderCallbacks implementation

    override final fun onCreateLoader(id: Int, args: Bundle?): Loader<P> {
        return PresenterLoader(context, onPresenterFactoryCreated())
    }

    override final fun onLoadFinished(loader: Loader<P>, presenter: P) {
        if(this.presenter == null) {
            this.presenter = presenter
        }
        this.presenter?.onViewAttached(getMvpView())
    }

    override final fun onLoaderReset(loader: Loader<P>) {
        presenter?.onDestroy()
    }

}