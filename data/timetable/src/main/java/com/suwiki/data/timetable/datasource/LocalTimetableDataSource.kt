package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.Timetable
import kotlinx.coroutines.flow.Flow

interface LocalTimetableDataSource {
  suspend fun getAllTimetable(): List<Timetable>

  suspend fun getTimetable(createTime: Long): Timetable?

  suspend fun setMainTimetableCreateTime(createTime: Long)

  suspend fun getMainTimetableCreateTime(): Flow<Long?>

  suspend fun deleteAllTimetable()

  suspend fun deleteTimetable(data: Timetable)

  suspend fun updateTimetable(data: Timetable)
  suspend fun insertTimetable(data: Timetable)
}
