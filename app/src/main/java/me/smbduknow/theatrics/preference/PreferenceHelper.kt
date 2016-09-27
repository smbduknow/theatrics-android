package me.smbduknow.theatrics.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import me.smbduknow.theatrics.BuildConfig

object PreferenceHelper {

    private val PACKAGE_NAME = BuildConfig.APPLICATION_ID
    private val KEY_USER_CITY = PACKAGE_NAME + ".KEY_USER_CITY"

    private lateinit var pref: SharedPreferences

    fun init(ctx: Context) {
        this.pref = PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun getUserCity() = pref.getString(KEY_USER_CITY, "")
    fun setUserCity(slug: String) = pref.edit().putString(KEY_USER_CITY, slug).apply()

    fun remove(key: String) = pref.edit().remove(key).apply()

    fun clear() = pref.edit().clear().apply()

}