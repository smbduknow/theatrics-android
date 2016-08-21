package me.smbduknow.theatrics.ui.activity

import android.os.Bundle
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

}