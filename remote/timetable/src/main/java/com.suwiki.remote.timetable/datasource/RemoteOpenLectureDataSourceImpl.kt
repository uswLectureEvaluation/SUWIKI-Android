package com.suwiki.remote.timetable.datasource

import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.RemoteOpenLectureDataSource
import com.suwiki.remote.timetable.api.OpenLectureApi
import com.suwiki.remote.timetable.response.toModel
import javax.inject.Inject

class RemoteOpenLectureDataSourceImpl @Inject constructor(
  private val openLectureApi: OpenLectureApi,
) : RemoteOpenLectureDataSource {

  override suspend fun getOpenLectureList(
    cursorId: Long,
    size: Long,
    keyword: String?,
    major: String?,
    grade: Int?,
  ) = openLectureApi.getOpenLectureList(
    cursorId = cursorId,
    size = size,
    keyword = keyword,
    major = major,
    grade = grade,
  ).getOrThrow().toModel()
}
