package com.suwiki.data.timetable.repository

import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.OpenLectureData
import com.suwiki.data.timetable.datasource.RemoteOpenLectureDataSource
import com.suwiki.domain.timetable.repository.OpenLectureRepository
import javax.inject.Inject

class OpenLectureRepositoryImpl @Inject constructor(
  private val remoteOpenLectureDataSource: RemoteOpenLectureDataSource,
) : OpenLectureRepository {
  override suspend fun getOpenLectureList(
    cursorId: Long,
    size: Long,
    keyword: String?,
    major: String?,
    grade: Int?,
  ): OpenLectureData = remoteOpenLectureDataSource.getOpenLectureList(
    cursorId = cursorId,
    size = size,
    keyword = keyword,
    major = major,
    grade = grade,
  )
}
