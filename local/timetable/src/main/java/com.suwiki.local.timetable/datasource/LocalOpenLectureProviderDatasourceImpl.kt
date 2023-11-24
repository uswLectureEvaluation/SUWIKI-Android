package com.suwiki.local.timetable.datasource

import com.suwiki.core.database.OpenLectureDatabase
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.LocalOpenLectureProviderDatasource
import com.suwiki.local.timetable.converter.toModel
import javax.inject.Inject

class LocalOpenLectureProviderDatasourceImpl @Inject constructor(
  private val openLectureDatabase: OpenLectureDatabase,
) : LocalOpenLectureProviderDatasource {

  override suspend fun getOpenLectureList(): List<OpenLecture> {
    return openLectureDatabase.openLectureDao().getAll().map { it.toModel() }
  }
}
