package com.suwiki.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.database.FirebaseDatabase
import com.suwiki.data.db.versionPreferences
import com.suwiki.domain.model.Result
import com.suwiki.domain.model.SuwikiError
import com.suwiki.domain.repository.VersionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
  @ApplicationContext private val context: Context,
  private val firebaseDatabase: FirebaseDatabase,
) : VersionRepository {
  private val dataStore: DataStore<Preferences>
    get() = context.versionPreferences

  private val data
    get() = dataStore.data

  override val appVersion: String
    get() = runBlocking {
      data.map {
        it[APP_VERSION]
      }.first() ?: "$DEFAULT_APP_VERSION"
    }
  override val openMajorVersion: Float
    get() = runBlocking {
      data.map {
        it[OPEN_MAJOR_VERSION]
      }.first() ?: 0f
    }
  override val remoteAppVersion: Flow<Result<Long>>
    get() = callbackFlow {
      firebaseDatabase.getReference("version").get().addOnSuccessListener {
        trySend(Result.Success(it.value as? Long ?: DEFAULT_APP_VERSION))
        close()
      }.addOnFailureListener {
        trySend(Result.Failure(SuwikiError.NetworkError))
        close()
      }
      awaitClose()
    }

  override suspend fun setAppVersion(version: String) {
    dataStore.edit {
      it[APP_VERSION] = version
    }
  }

  override suspend fun setOpenMajorVersion(version: Float) {
    dataStore.edit {
      it[OPEN_MAJOR_VERSION] = version
    }
  }

  companion object {
    private val APP_VERSION = stringPreferencesKey("[KEY] app version")
    private val OPEN_MAJOR_VERSION = floatPreferencesKey("[KEY] open major version")
    const val DEFAULT_APP_VERSION = 202107271830
  }
}
