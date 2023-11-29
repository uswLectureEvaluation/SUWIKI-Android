package com.suwiki.local.timetable.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.suwiki.core.database.database.OpenLectureDatabase
import com.suwiki.core.database.di.NormalDataStore
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.LocalOpenLectureDatasource
import com.suwiki.local.timetable.converter.toEntity
import com.suwiki.local.timetable.converter.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalOpenLectureDatasourceImpl @Inject constructor(
  @NormalDataStore private val dataStore: DataStore<Preferences>,
  private val openLectureDatabase: OpenLectureDatabase,
) : LocalOpenLectureDatasource {

  companion object {
    private val LOCAL_OPEN_LECTURE_VERSION = longPreferencesKey("[KEY] is local open lecture version")
  }

  private val data: Flow<Preferences>
    get() = dataStore.data

  override suspend fun setOpenLectureListVersion(version: Long) {
    dataStore.edit { it[LOCAL_OPEN_LECTURE_VERSION] = version }
  }

  override suspend fun getOpenLectureListVersion(): Flow<Long> {
    return data.map { it[LOCAL_OPEN_LECTURE_VERSION] ?: 0L }
  }

  override suspend fun getOpenLectureList(): List<OpenLecture> {
    return openLectureDatabase.openLectureDao().getAll().map { it.toModel() }
  }

  override suspend fun insertOpenLecture(data: OpenLecture) {
    openLectureDatabase.openLectureDao().insert(data.toEntity())
  }

  override suspend fun deleteAllOpenLecture() {
    openLectureDatabase.openLectureDao().deleteAll()
  }
}
