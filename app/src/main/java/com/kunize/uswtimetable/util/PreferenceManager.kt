package com.kunize.uswtimetable.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager {
    companion object {
        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        }

        fun setBoolean(context: Context, key: String, value: Boolean) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun getBoolean(context: Context, key: String): Boolean {
            val prefs = getPreferences(context)
            return prefs.getBoolean(key, DEFAULT_BOOLEAN_VALUE)
        }

        fun setString(context: Context, key: String, value: String) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getString(context: Context, key: String): String? {
            val prefs = getPreferences(context)
            return prefs.getString(key, DEFAULT_STRING_VALUE)
        }

        fun setInt(context: Context, key: String, value: Int) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun getInt(context: Context, key: String):Int {
            val prefs = getPreferences(context)
            return prefs.getInt(key, DEFAULT_INT_VALUE)
        }

        fun saveAccessToken(context: Context, value: String) {
            setString(context, ACCESS_TOKEN, value)
        }

        fun getAccessToken(context: Context) {
            getString(context, ACCESS_TOKEN)
        }

        fun saveRefreshToken(context: Context, value: String) {
            setString(context, REFRESH_TOKEN, value)
        }

        fun getRefreshToken(context: Context) {
            getString(context, REFRESH_TOKEN)
        }

        private const val PREFERENCE_NAME = "APP_SETTING"
        private const val DEFAULT_BOOLEAN_VALUE = false
        private const val DEFAULT_STRING_VALUE = ""
        private const val DEFAULT_INT_VALUE = 0
        private const val ACCESS_TOKEN = "access"
        private const val REFRESH_TOKEN = "refresh"
    }
}