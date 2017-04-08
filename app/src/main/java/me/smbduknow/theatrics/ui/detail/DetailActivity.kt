package me.smbduknow.theatrics.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_detail.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.misc.loadImg

class DetailActivity : AppCompatActivity() {

    private val LAYOUT_RES = R.layout.activity_detail

    private val mToolbar by lazy { detail_toolbar }
    private val mCollapsingToolbar by lazy { detail_collapsing_toolbar }

    private val mCollapsingImage by lazy { detail_image }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RES)

        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.detail_scroll_view, DetailEventFragment.newInstance(intent.extras))
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        mCollapsingToolbar.title = title
    }

    fun setCollapsingImage(image: String) {
        mCollapsingImage.loadImg(image)
    }

}