package me.smbduknow.kedditkotlin.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*
import me.smbduknow.kedditkotlin.R
import me.smbduknow.kedditkotlin.ui.commons.InfiniteScrollListener
import me.smbduknow.kedditkotlin.ui.commons.inflate
import me.smbduknow.kedditkotlin.ui.model.UiEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class EventsFragment : Fragment() {

    private val eventsList by lazy { news_list }

    private val eventsManager by lazy { EventsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayout = LinearLayoutManager(context)

        eventsList.apply {
            setHasFixedSize(true)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(linearLayout, {requestNews()} ))
            adapter = EventsAdapter()
        }

        if (savedInstanceState == null) {
            requestNews()
        }
    }

    private fun requestNews() {
        eventsManager.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.items.map { UiEvent(
                            it.title,
                            it.type,
                            it.description ) } }
                .subscribe { news -> (eventsList.adapter as EventsAdapter).addNews(news) }
    }
}
