package me.smbduknow.theatrics.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.base.BaseActivity
import me.smbduknow.theatrics.ui.feed.FeedEventFragment

class MainActivity : BaseActivity() {

    private val menuView by lazy { main_menu }
    private val menuButton by lazy { menu_btn }
    private val searchButton by lazy { search_btn }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content_main, FeedEventFragment())
                    .commit()
        }

        menuView.visibility = View.GONE

        menuButton.setOnClickListener { v ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                val drawable = resources.getDrawable(R.drawable.animated_drawable, null) as AnimatedVectorDrawable
                menuButton.setImageDrawable(drawable)

                val finalRadius = Math.max(menuView.width, menuView.height).toFloat()

                if(menuView.visibility == View.VISIBLE) {
                    menuView.visibility = View.GONE
                    menuView.animate().alpha(0f).withEndAction { menuView.visibility = View.VISIBLE  }
                    searchButton.animate().alpha(0f).withEndAction { searchButton.visibility = View.VISIBLE  }
                } else {
                    drawable.start()
                    menuView.visibility = View.VISIBLE
                    ViewAnimationUtils.createCircularReveal(menuView, 0, 0, 0f, finalRadius).start()
                    searchButton.animate().alpha(0f).withEndAction { searchButton.visibility = View.GONE  }
                }
            }
        }
    }

}
