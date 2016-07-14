package me.smbduknow.theatrics.presenter

import android.content.Context
import android.support.v4.content.Loader

class FeedPresenterLoader(context: Context) : Loader<FeedPresenter>(context) {

    private var presenter: FeedPresenter? = null

    override fun onStartLoading() {

        // If we already own an instance, simply deliver it.
        if (presenter != null) {
            deliverResult(presenter)
            return
        }

        // Otherwise, force a load
        forceLoad()
    }

    override fun onForceLoad() {
        // Create the Presenter using the Factory
        presenter = FeedPresenter()

        // Deliver the result
        deliverResult(presenter)
    }

    override fun onReset() {
        presenter = null
    }
}