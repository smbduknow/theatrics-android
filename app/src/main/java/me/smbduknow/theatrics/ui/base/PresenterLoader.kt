package me.smbduknow.theatrics.ui.base

import android.content.Context
import android.support.v4.content.Loader

class PresenterLoader<P : MvpPresenter<*>>(
        context: Context,
        val presenterFactory: PresenterFactory<P>
) : Loader<P>(context) {

    private var presenter: P? = null

    override fun onStartLoading() {
        if (presenter == null)
            forceLoad()
        else
            deliverResult(presenter)
    }

    override fun onForceLoad() {
        presenter = presenterFactory.create()
        deliverResult(presenter)
    }

    override fun onReset() {
        presenter = null
    }


}