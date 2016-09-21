package me.smbduknow.theatrics.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import io.codetail.animation.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_menu.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.base.BaseActivity
import me.smbduknow.theatrics.ui.feed.FeedEventFragment

class MainActivity : BaseActivity() {

    private val menuView by lazy { main_menu }
    private val menuButton by lazy { menu_btn }
    private val searchButton by lazy { search_btn }

    private val citySpinner by lazy { cities_spinner }

    private var arrowDrawable: DrawerArrowDrawable? = null

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


        arrowDrawable = DrawerArrowDrawable(this)
        menuButton.setImageDrawable(arrowDrawable)

        menuButton.setOnClickListener { v ->
            if(menuView.visibility == View.GONE) {
                showMenuView()
                showSearchIcon(false)
            }
            else {
                showMenuView(false)
                showSearchIcon()
            }
        }



        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                listOf("Санкт-Петербург", "Москва"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

    }

    override fun onBackPressed() {
        if(menuView.visibility == View.VISIBLE) {
            showMenuView(false)
            showSearchIcon()
        } else {
            super.onBackPressed()
        }
    }

    private fun showMenuView(visible: Boolean = true) {
        menuView.visibility = View.VISIBLE
        val parent = menuView.parent as View
        menuView.measure(
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY))
        val revealRadius = Math.hypot(
                menuView.measuredWidth.toDouble(),
                menuView.measuredHeight.toDouble()).toFloat()

        val animator: Animator

        if(visible) {
            animator = ViewAnimationUtils.createCircularReveal(menuView, 0, 0, 0f, revealRadius)
            arrowDrawable?.progress = 1f
        } else {
            animator = ViewAnimationUtils.createCircularReveal(menuView, 0, 0, revealRadius, 0f)
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    menuView.visibility = View.GONE
                }
            })
            arrowDrawable?.progress = 0f
        }

        animator.start()
    }


    protected fun showSearchIcon(visible: Boolean = true) {
        searchButton.animate().alpha(if (visible) 1f else 0f).start()
    }

}
