package com.suwiki.data.datasource.local

import com.suwiki.core.model.timetable.OpenLecture

interface LocalTimetableStorageDatasource {
  suspend fun insert(data: OpenLecture)
  suspend fun deleteAllLocalTimetable()
  suspend fun deleteLocalTimetable(data: OpenLecture)
  suspend fun updateLocalTimetable(data: OpenLecture)
}
