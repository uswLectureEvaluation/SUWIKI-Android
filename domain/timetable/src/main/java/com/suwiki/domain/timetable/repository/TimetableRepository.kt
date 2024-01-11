package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.Timetable
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {
  suspend fun getAllTimetable(): List<Timetable>

  suspend fun getTimetable(createTime: Long): Timetable?

  suspend fun setMainTimetableCreateTime(createTime: Long)

  suspend fun getMainTimetableCreateTime(): Flow<Long>

  suspend fun deleteTimetable(data: Timetable)

  suspend fun updateTimetable(data: Timetable)
}
