package com.suwiki.local.timetable.datasource

import com.suwiki.core.database.database.OpenLectureDatabase
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.LocalOpenLectureDatasource
import com.suwiki.local.timetable.converter.toEntity
import com.suwiki.local.timetable.converter.toModel
import javax.inject.Inject

class LocalOpenLectureDatasourceImpl @Inject constructor(
  private val openLectureDatabase: OpenLectureDatabase,
) : LocalOpenLectureDatasource {

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
