package me.smbduknow.theatrics.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.fragment.DetailEventFragment

class DetailActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        if (savedInstanceState == null) {
            changeFragment(R.id.fragment_container, DetailEventFragment())
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

    public fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}