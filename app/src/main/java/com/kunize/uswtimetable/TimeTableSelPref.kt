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

    fun getInt(key: String, defValue: Int) : Int {
        return prefs.getInt(key, defValue)
    }

    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
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

    fun saveAccessToken(value: String) = setString(ACCESS_TOKEN, value)

    fun getAccessToken() = getString(ACCESS_TOKEN, "")

    fun saveRefreshToken(value: String) = setString(REFRESH_TOKEN, value)

    fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")


    companion object {
        private const val ACCESS_TOKEN = "access"
        private const val REFRESH_TOKEN = "refresh"
    }
}
