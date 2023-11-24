package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture

interface LocalTimetableStorageDatasource {
  suspend fun insertLocalTimetableItem(data: OpenLecture)
  suspend fun deleteAllLocalTimetable()
  suspend fun deleteLocalTimetableItem(data: OpenLecture)
  suspend fun updateLocalTimetableItem(data: OpenLecture)
}
