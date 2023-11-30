package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.Timetable

interface TimetableRepository {
  suspend fun getAllTimetable(): List<Timetable>

  suspend fun getTimetable(createTime: Long): Timetable

  suspend fun setMainTimetableCreateTime(createTime: Long)

  suspend fun deleteTimetable(data: Timetable)

  suspend fun updateTimetable(data: Timetable)
}
