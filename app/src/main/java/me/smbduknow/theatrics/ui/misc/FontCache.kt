package me.smbduknow.theatrics.ui.misc

import android.content.Context
import android.graphics.Typeface
import java.util.*

object FontCache {

    private val fontCache = Hashtable<String, Typeface>()

    @JvmStatic
    fun get(name: String, context: Context): Typeface? {
        var typeface: Typeface? = fontCache[name]
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, "fonts/${name}.ttf")
            } catch (e: Exception) {
                return null
            }
            fontCache.put(name, typeface)
        }
        return typeface
    }
}
