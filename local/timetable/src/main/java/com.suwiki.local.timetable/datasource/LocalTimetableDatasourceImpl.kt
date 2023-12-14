package com.suwiki.local.timetable.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.suwiki.core.android.Dispatcher
import com.suwiki.core.android.SuwikiDispatchers
import com.suwiki.core.database.database.TimetableDatabase
import com.suwiki.core.database.di.NormalDataStore
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.data.timetable.datasource.LocalTimetableDataSource
import com.suwiki.local.timetable.converter.toEntity
import com.suwiki.local.timetable.converter.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalTimetableDatasourceImpl @Inject constructor(
  @NormalDataStore private val dataStore: DataStore<Preferences>,
  @Dispatcher(SuwikiDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
  private val timetableDatabase: TimetableDatabase,
) : LocalTimetableDataSource {

  companion object {
    private val MAIN_TIMETABLE_CREATE_TIME = longPreferencesKey("[KEY] is main timetable create time")
  }

  private val data: Flow<Preferences>
    get() = dataStore.data

  override suspend fun getAllTimetable(): List<Timetable> = withContext(ioDispatcher) {
    timetableDatabase.timetableDao().getAll().map { it.toModel() }
  }

  override suspend fun getTimetable(createTime: Long): Timetable = withContext(ioDispatcher) {
    timetableDatabase.timetableDao().get(createTime).toModel()
  }

  override suspend fun deleteAllTimetable() = withContext(ioDispatcher) {
    timetableDatabase.timetableDao().deleteAll()
  }

  override suspend fun deleteTimetable(data: Timetable) = withContext(ioDispatcher) {
    timetableDatabase.timetableDao().delete(data.toEntity())
  }

  override suspend fun updateTimetable(data: Timetable) = withContext(ioDispatcher) {
    timetableDatabase.timetableDao().update(data.toEntity())
  }

  override suspend fun setMainTimetableCreateTime(createTime: Long) {
    dataStore.edit { it[MAIN_TIMETABLE_CREATE_TIME] = createTime }
  }

  override suspend fun getMainTimetableCreateTime(): Flow<Long> {
    return data.map { it[MAIN_TIMETABLE_CREATE_TIME] ?: 0L }
  }
}
