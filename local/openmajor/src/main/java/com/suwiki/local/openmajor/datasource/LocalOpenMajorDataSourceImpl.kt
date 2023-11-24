package com.suwiki.local.openmajor.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.suwiki.core.database.database.OpenMajorDatabase
import com.suwiki.core.database.di.NormalDataStore
import com.suwiki.core.model.openmajor.OpenMajor
import com.suwiki.data.openmajor.datasource.LocalOpenMajorDataSource
import com.suwiki.local.openmajor.converter.toEntity
import com.suwiki.local.openmajor.converter.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalOpenMajorDataSourceImpl @Inject constructor(
    @NormalDataStore private val dataStore: DataStore<Preferences>,
    private val db: OpenMajorDatabase,
) : LocalOpenMajorDataSource {

  companion object {
    private val OPEN_MAJOR_VERSION = floatPreferencesKey("[KEY] is open major version")
  }

  private val data: Flow<Preferences>
    get() = dataStore.data

  override suspend fun getLocalOpenMajorVersion(): Flow<Float> =
    data.map { it[OPEN_MAJOR_VERSION] ?: 0f }

  override suspend fun setLocalOpenMajorVersion(version: Float) {
    dataStore.edit { it[OPEN_MAJOR_VERSION] = version }
  }

  override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
    return db.openMajorDao().getAll().map { it.toModel() }
  }

  override suspend fun saveAllOpenMajors(majors: List<OpenMajor>) {
    db.openMajorDao().insertAll(majors.map { it.toEntity() })
  }

  override suspend fun deleteAllOpenMajors() {
    db.openMajorDao().deleteAll()
  }
}
