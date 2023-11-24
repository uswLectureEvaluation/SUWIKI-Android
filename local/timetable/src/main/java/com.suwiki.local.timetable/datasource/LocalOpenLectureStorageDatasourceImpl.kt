package com.suwiki.local.timetable.datasource

import com.suwiki.core.database.database.OpenLectureDatabase
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.LocalOpenLectureStorageDatasource
import com.suwiki.local.timetable.converter.toEntity
import javax.inject.Inject

class LocalOpenLectureStorageDatasourceImpl @Inject constructor(
  private val openLectureDatabase: OpenLectureDatabase,
) : LocalOpenLectureStorageDatasource {

  override suspend fun insertOpenLecture(data: OpenLecture) {
    openLectureDatabase.openLectureDao().insert(data.toEntity())
  }

  override suspend fun deleteAllOpenLecture() {
    openLectureDatabase.openLectureDao().deleteAll()
  }
}
