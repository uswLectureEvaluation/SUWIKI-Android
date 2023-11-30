package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture

interface RemoteOpenLectureDataSource {
  suspend fun getOpenLectureListVersion(): Long
  suspend fun getOpenLectureList(): List<OpenLecture>
}
