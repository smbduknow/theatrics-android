package me.smbduknow.theatrics.ui.misc

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import me.smbduknow.theatrics.R
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ViewGroup.inflate(inflater: LayoutInflater, layoutId: Int, attachToRoot: Boolean = false): View {
    return inflater.inflate(layoutId, this, attachToRoot)
}


fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.with(context).load(R.drawable.pic_placeholder).into(this)
    } else {
        Picasso.with(context).load(imageUrl).into(this)
    }
}

fun Date.format(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}