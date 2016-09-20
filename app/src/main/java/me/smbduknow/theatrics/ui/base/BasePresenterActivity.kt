package me.smbduknow.theatrics.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader


abstract class BasePresenterActivity<P : MvpPresenter<V>, V : MvpView> : BaseActivity(),
        LoaderManager.LoaderCallbacks<P> {

    private val LOADER_ID = 1

    protected var presenter: P? = null

    abstract fun onPresenterFactoryCreated() : PresenterFactory<P>


    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportLoaderManager.initLoader(LOADER_ID, null, this)
    }

    @CallSuper
    override  fun onDestroy() {
        presenter?.onViewDetached()
        super.onDestroy()
    }


    protected fun getMvpView(): V {
        @Suppress("UNCHECKED_CAST")
        return this as V
    }


    // LoaderCallbacks implementation

    override final fun onCreateLoader(id: Int, args: Bundle?): Loader<P> {
        return PresenterLoader(this, onPresenterFactoryCreated())
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