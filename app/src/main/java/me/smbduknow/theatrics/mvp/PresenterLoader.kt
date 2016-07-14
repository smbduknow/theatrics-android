package me.smbduknow.theatrics.mvp

import android.content.Context
import android.support.v4.content.Loader

class PresenterLoader<P : MvpPresenter<V>, V : MvpView>(
        context: Context,
        val fConstructor: () -> P
) : Loader<P>(context) {

    private var presenter: P? = null

    override fun onStartLoading() {
        if (presenter != null) deliverResult(presenter)
        else forceLoad()
    }

    override fun onForceLoad() {
        presenter = fConstructor()
        deliverResult(presenter)
    }

    override fun onReset() {
        presenter = null
    }
}