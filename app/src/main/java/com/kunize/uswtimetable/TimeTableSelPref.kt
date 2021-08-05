package com.kunize.uswtimetable

import android.app.Application
import android.content.Context

class TimeTableSelPref : Application() {
    companion object {
        lateinit var prefs: PrefsManager
    }

    override fun onCreate() {
        prefs = PrefsManager(applicationContext)
        super.onCreate()
    }

}

class PrefsManager(context: Context) {
    private val prefs = context.getSharedPreferences("timetableSel", Context.MODE_PRIVATE)

    fun getLong(key: String, defValue: Long) : Long {
        return prefs.getLong(key, defValue)
    }

    fun setLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun getString(key: String, defValue: String) : String {
        return prefs.getString(key, defValue)!!
    }

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}
