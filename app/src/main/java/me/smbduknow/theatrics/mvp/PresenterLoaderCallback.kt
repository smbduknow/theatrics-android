package me.smbduknow.theatrics.mvp


import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader

class PresenterLoaderCallback<P : MvpPresenter<V>, V : MvpView>(
        val context: Context,
        val fPresenterConstructor: () -> P,
        val fOnFinish: (P) -> Unit,
        val fOnReset: () -> Unit
) : LoaderManager.LoaderCallbacks<P> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<P> {
        return PresenterLoader(context, fPresenterConstructor)
    }

    override fun onLoadFinished(loader: Loader<P>, presenter: P) {
        fOnFinish(presenter)
    }

    override fun onLoaderReset(loader: Loader<P>) {
        fOnReset()
    }

}