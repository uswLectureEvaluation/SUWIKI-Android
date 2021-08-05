package com.kunize.uswtimetable

import android.app.Application
import android.content.Context

class ImagePref : Application() {
    companion object {
        lateinit var prefs: ImageManager
    }

    override fun onCreate() {
        prefs = ImageManager(applicationContext)
        super.onCreate()
    }

}

class ImageManager(context: Context) {
    private val prefs = context.getSharedPreferences("image", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String) : String {
        return prefs.getString(key, defValue)!!
    }

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}