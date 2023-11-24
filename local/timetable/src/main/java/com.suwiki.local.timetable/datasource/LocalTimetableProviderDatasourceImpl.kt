package com.suwiki.local.timetable.datasource

import com.suwiki.core.database.TimetableDatabase
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.data.timetable.datasource.LocalTimetableProviderDatasource
import com.suwiki.local.timetable.converter.toModel
import javax.inject.Inject

class LocalTimetableProviderDatasourceImpl @Inject constructor(
  private val timetableDatabase: TimetableDatabase,
) : LocalTimetableProviderDatasource {

  override suspend fun getLocalTimetable(): List<Timetable> {
    return timetableDatabase.timetableDao().getAll().map { it.toModel() }
  }
}
