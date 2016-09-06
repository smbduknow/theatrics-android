package me.smbduknow.theatrics.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_detail.*
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.commons.loadImg
import me.smbduknow.theatrics.ui.fragment.DetailEventFragment

class DetailActivity : BaseFragmentActivity() {

    private val mToolbar by lazy { detail_toolbar }
    private val mCollapsingToolbar by lazy { detail_collapsing_toolbar }

    private val mCollapsingImage by lazy { detail_image }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.transparent_black)
        }

        if (savedInstanceState == null) {
            changeFragment(R.id.detail_scroll_view, DetailEventFragment.newInstance(intent.extras))
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