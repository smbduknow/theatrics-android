package me.smbduknow.kedditkotlin

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_news.view.*
import me.smbduknow.kedditkotlin.commons.inflate
import me.smbduknow.kedditkotlin.commons.loadImg
import me.smbduknow.kedditkotlin.model.RedditNews

class NewsDelegateAdapter : ViewModelDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewModel) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNews)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_news)) {

        fun bind(item: RedditNews) = with(itemView) {
//            Picasso.with(itemView.context).load(item.thumbnail).into(img_thumbnail)
            img_thumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
//            time.text = item.created.getFriendlyTime()
        }
    }
}