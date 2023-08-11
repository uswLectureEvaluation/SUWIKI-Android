package com.suwiki.local.datastore

import kotlinx.coroutines.flow.Flow
import tech.thdev.useful.encrypted.data.store.preferences.ksp.annotations.UsefulPreferences
import tech.thdev.useful.encrypted.data.store.preferences.ksp.annotations.value.ClearValues
import tech.thdev.useful.encrypted.data.store.preferences.ksp.annotations.value.GetValue
import tech.thdev.useful.encrypted.data.store.preferences.ksp.annotations.value.SetValue

@UsefulPreferences(/* option. Not use security - disableSecurity = true */)
interface SecurityPreferences {

    @GetValue(KEY_ACCESS_TOKEN, defaultValue = "")
    fun flowAccessToken(): Flow<String>

    @SetValue(KEY_ACCESS_TOKEN)
    suspend fun setAccessToken(value: String)

    @GetValue(KEY_REFRESH_TOKEN, defaultValue = "")
    fun flowRefreshToken(): Flow<String>

    @SetValue(KEY_REFRESH_TOKEN)
    suspend fun setRefreshToken(value: String)

    @ClearValues
    suspend fun clearAll()

    companion object {

        private const val KEY_ACCESS_TOKEN = "key-access-token"
        private const val KEY_REFRESH_TOKEN = "key-refresh-token"
    }
}