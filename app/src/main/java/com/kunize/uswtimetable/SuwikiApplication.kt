package com.kunize.uswtimetable

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SuwikiApplication : Application() {
    companion object {
        lateinit var prefs: PrefsManager
        lateinit var encryptedPrefs: EncryptedPrefsManger
        lateinit var instance: SuwikiApplication
    }

    override fun onCreate() {
        instance = this
        prefs = PrefsManager(applicationContext)
        encryptedPrefs = EncryptedPrefsManger(applicationContext)
        super.onCreate()
    }
}

class EncryptedPrefsManger(context: Context) {
    var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "encryptedValues",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    fun saveAccessToken(value: String) = setString(ACCESS_TOKEN, value)

    fun getAccessToken() = getString(ACCESS_TOKEN)

    fun saveRefreshToken(value: String) = setString(REFRESH_TOKEN, value)

    fun getRefreshToken() = getString(REFRESH_TOKEN)

    private fun setString(key: String, value: String) =
        sharedPreferences.edit().putString(key, value).apply()

    private fun getString(key: String) = sharedPreferences.getString(key, "")

    companion object {
        private const val ACCESS_TOKEN = "access"
        private const val REFRESH_TOKEN = "refresh"
    }
}

class PrefsManager(context: Context) {
    private val prefs = context.getSharedPreferences("timetableSel", Context.MODE_PRIVATE)

    fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        return prefs.getLong(key, defValue)
    }

    fun setLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue)!!
    }

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}
