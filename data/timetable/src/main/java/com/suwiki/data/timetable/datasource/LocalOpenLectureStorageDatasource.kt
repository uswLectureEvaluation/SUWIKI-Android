package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture

interface LocalOpenLectureStorageDatasource {
  suspend fun insertOpenLecture(data: OpenLecture)
  suspend fun deleteAllOpenLecture()
}
