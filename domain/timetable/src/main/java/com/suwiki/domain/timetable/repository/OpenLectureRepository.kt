package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.OpenLectureData
import kotlinx.coroutines.flow.Flow

interface OpenLectureRepository {
  suspend fun getOpenLectureList(
    cursorId: Long,
    size: Long,
    keyword: String?,
    major: String?,
    grade: Int?,
  ): OpenLectureData
}
