package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.OpenLecture

interface OpenLectureRepository {
  suspend fun getOpenLectureList(): List<OpenLecture>
}
