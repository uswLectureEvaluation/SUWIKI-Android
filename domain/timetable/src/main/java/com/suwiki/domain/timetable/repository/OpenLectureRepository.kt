package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.OpenLecture
import kotlinx.coroutines.flow.Flow

interface OpenLectureRepository {
  suspend fun getOpenLectureList(): Flow<List<OpenLecture>>
}
