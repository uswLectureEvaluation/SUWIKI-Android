package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture

interface LocalOpenLectureProviderDatasource {
  suspend fun getOpenLectureList(): List<OpenLecture>
}
