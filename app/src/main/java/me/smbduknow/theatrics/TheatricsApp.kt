package me.smbduknow.theatrics

import android.app.Application
import me.smbduknow.theatrics.preference.PreferenceHelper

class TheatricsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        PreferenceHelper.init(this)
    }

}