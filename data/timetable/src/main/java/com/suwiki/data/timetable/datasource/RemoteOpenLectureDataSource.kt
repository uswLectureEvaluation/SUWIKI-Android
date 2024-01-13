package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture

interface RemoteOpenLectureDataSource {
  suspend fun getOpenLectureList(
    cursorId: Long,
    size: Long,
    keyword: String?,
    major: String?,
    grade: Int?,
  ): List<OpenLecture>
}
