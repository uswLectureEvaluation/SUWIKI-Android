package com.suwiki.data.timetable.datasource

import com.suwiki.common.model.timetable.OpenLectureData

interface RemoteOpenLectureDataSource {
  suspend fun getOpenLectureList(
    cursorId: Long,
    size: Long,
    keyword: String?,
    major: String?,
    grade: Int?,
  ): OpenLectureData
}
