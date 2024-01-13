package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.OpenLectureData

interface OpenLectureRepository {
  suspend fun getOpenLectureList(
    cursorId: Long,
    size: Long,
    keyword: String?,
    major: String?,
    grade: Int?,
  ): OpenLectureData
}
