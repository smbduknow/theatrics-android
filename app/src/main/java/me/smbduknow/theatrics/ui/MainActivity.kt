package me.smbduknow.theatrics.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import io.codetail.animation.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_menu.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.preference.PreferenceHelper
import me.smbduknow.theatrics.ui.feed.FeedEventFragment
import me.smbduknow.theatrics.ui.feed.adapter.CityArrayAdapter
import me.smbduknow.theatrics.ui.misc.DatepickerDialog
import me.smbduknow.theatrics.ui.model.UiCity

class MainActivity : AppCompatActivity() {

    private val LAYOUT_RES = R.layout.activity_main

    private val menuView by lazy { main_menu }
    private val menuButton by lazy { menu_btn }
    private val searchButton by lazy { search_btn }

    private val citySpinner by lazy { cities_spinner }

    private val calendarBtn by lazy { calendar_btn }

    private var arrowDrawable: DrawerArrowDrawable? = null

    private var isCityChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RES)
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

        // TODO request from API
        val adapter = CityArrayAdapter(this, android.R.layout.simple_spinner_item, listOf(
                UiCity("msk",           "Москва"),
                UiCity("spb",           "Санкт-Петербург"),
                UiCity("ekb",           "Екатеринбург"),
                UiCity("kev",           "Киев"),
                UiCity("krasnoyarsk",   "Красноярск"),
                UiCity("kzn",           "Казань"),
                UiCity("nnv",           "Нижний Новгород"),
                UiCity("nsk",           "Новосибирск"),
                UiCity("sochi",         "Сочи")
        ))
        citySpinner.adapter = adapter
        citySpinner.setSelection(adapter.findPositionBySlug(PreferenceHelper.getUserCity()))
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                PreferenceHelper.setUserCity(adapter.objects[position].slug)
                isCityChanged = true
            }
        }

        calendarBtn.setOnClickListener {
            val datepicker = DatepickerDialog()
            datepicker.resultListener = { date ->
                val feedFragment = supportFragmentManager.findFragmentById(R.id.content_main) as FeedEventFragment
                feedFragment.date = date
                feedFragment.refreshFeed()
            }
            datepicker.show(supportFragmentManager, "datepicker")
        }
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
            onMenuClosed()
        }

        animator.start()
    }

    protected fun showSearchIcon(visible: Boolean = true) {
        searchButton.animate().alpha(if (visible) 1f else 0f).start()
    }

    protected fun onMenuClosed() {
        if(isCityChanged) {
            isCityChanged = false
            val feedFragment = supportFragmentManager.findFragmentById(R.id.content_main) as FeedEventFragment
            feedFragment.refreshFeed()
        }
    }

}
