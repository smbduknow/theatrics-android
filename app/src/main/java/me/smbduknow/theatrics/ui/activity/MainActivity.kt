package me.smbduknow.theatrics.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import me.smbduknow.theatrics.R
import me.smbduknow.theatrics.ui.fragment.ListEventFragment

class MainActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            changeFragment(R.id.content_main, ListEventFragment())
        }
    }

}
