package com.suwiki.local.timetable.datasource

import com.suwiki.core.database.database.OpenLectureDatabase
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.LocalTimetableStorageDatasource
import com.suwiki.local.timetable.converter.toEntity
import javax.inject.Inject

class LocalTimetableStorageDatasourceImpl @Inject constructor(
  private val openLectureDatabase: OpenLectureDatabase,
) : LocalTimetableStorageDatasource {

  override suspend fun insertLocalTimetableItem(data: OpenLecture) {
    openLectureDatabase.openLectureDao().insert(data.toEntity())
  }

  override suspend fun deleteAllLocalTimetable() {
    openLectureDatabase.openLectureDao().deleteAll()
  }

  override suspend fun deleteLocalTimetableItem(data: OpenLecture) {
    openLectureDatabase.openLectureDao().delete(data.toEntity())
  }

  override suspend fun updateLocalTimetableItem(data: OpenLecture) {
    openLectureDatabase.openLectureDao().update(data.toEntity())
  }
}
