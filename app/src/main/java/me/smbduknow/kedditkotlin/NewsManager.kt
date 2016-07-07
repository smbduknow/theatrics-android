package me.smbduknow.kedditkotlin

import me.smbduknow.kedditkotlin.model.RedditNews
import rx.Observable

class NewsManager {

    fun getNews(): Observable<List<RedditNews>> {
        return Observable.create { subscriber ->
            val news = mutableListOf<RedditNews>()
            for (i in 1..10) {
                news.add(RedditNews(
                        "author$i",
                        "Title $i",
                        i, // number of comments
                        1457207701L - i * 200, // time
                        "http://lorempixel.com/200/200/technics/$i", // image url
                        "url"
                ))
            }
            subscriber.onNext(news)
            subscriber.onCompleted()
        }
    }
}