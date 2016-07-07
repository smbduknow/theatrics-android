package me.smbduknow.kedditkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*
import me.smbduknow.kedditkotlin.commons.inflate
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class NewsFragment : Fragment() {

    private val newsList by lazy { news_list }

    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsList.setHasFixedSize(true)
        newsList.layoutManager = LinearLayoutManager(context)

        newsList.adapter = NewsAdapter()

        if (savedInstanceState == null) {
            newsManager.getNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { news -> (newsList.adapter as NewsAdapter).addNews(news) }

        }
    }
}
