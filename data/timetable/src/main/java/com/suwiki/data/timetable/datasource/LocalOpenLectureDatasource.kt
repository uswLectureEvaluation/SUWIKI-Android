package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture

interface LocalOpenLectureDatasource {
  suspend fun getOpenLectureList(): List<OpenLecture>
  suspend fun insertOpenLecture(data: OpenLecture)
  suspend fun deleteAllOpenLecture()
}
