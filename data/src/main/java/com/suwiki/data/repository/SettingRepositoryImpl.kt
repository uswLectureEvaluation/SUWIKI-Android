package com.suwiki.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.suwiki.data.db.dataStore
import com.suwiki.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
  private val context: Context,
) : SettingRepository {

  private val dataStore: DataStore<Preferences>
    get() = context.dataStore

  private val data
    get() = dataStore.data

  override val isRememberLogin: Flow<Boolean>
    get() = data.map { it[REMEMBER_LOGIN] ?: false }

  override suspend fun setRememberLogin(remember: Boolean) {
    dataStore.edit {
      it[REMEMBER_LOGIN] = remember
    }
  }

  companion object {
    private val REMEMBER_LOGIN = booleanPreferencesKey("[KEY] remember login")
  }
}
