package com.suwiki.data.datasource.local

import com.suwiki.core.model.timetable.Timetable

interface LocalTimetableStorageDatasource {
  suspend fun insert(data: Timetable)
  suspend fun deleteAllLocalTimetable()
  suspend fun deleteLocalTimetable(data: Timetable)
  suspend fun updateLocalTimetable(data: Timetable)
}
