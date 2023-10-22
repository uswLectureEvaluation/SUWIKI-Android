package com.suwiki.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.suwiki.data.db.dataStore
import com.suwiki.data.network.ApiService
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  @ApplicationContext private val context: Context,
  @OtherApiService private val apiService: ApiService,
) : AuthRepository {
  private val dataSource: DataStore<Preferences>
    get() = context.dataStore

  private val data: Flow<Preferences>
    get() = dataSource.data

  override val refreshToken: Flow<String> = data.map { it[REFRESH_TOKEN] ?: "" }

  override val accessToken: Flow<String> = data.map { it[ACCESS_TOKEN] ?: "" }

  override suspend fun saveTokens(refresh: String, access: String) {
    dataSource.edit {
      it[REFRESH_TOKEN] = refresh
      it[ACCESS_TOKEN] = access
    }
  }

  override suspend fun clearTokens() {
    saveTokens("", "")
  }

  override suspend fun requestRefreshToken(): Boolean {
    val refresh = refreshToken.first()
    val response = apiService.requestRefresh(refresh).execute()

    return if (response.isSuccessful && response.body() != null) {
      response.body()?.let {
        saveTokens(it.refreshToken, it.accessToken)
        true
      } ?: run {
        false
      }
    } else {
      false
    }
  }

  companion object {
    private val REFRESH_TOKEN = stringPreferencesKey("[KEY] refresh token")
    private val ACCESS_TOKEN = stringPreferencesKey("[KEY] access token")
  }
}
