package com.suwiki.domain.timetable.repository

import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell

interface TimetableRepository {
  suspend fun getAllTimetable(): List<Timetable>

  suspend fun getTimetable(createTime: Long): Timetable?

  suspend fun setMainTimetableCreateTime(createTime: Long)

  suspend fun getMainTimetableCreateTime(): Long?

  suspend fun deleteTimetable(data: Timetable)

  suspend fun updateTimetable(
    createTime: Long,
    year: String,
    semester: String,
    name: String,
  )

  suspend fun insertTimetable(data: Timetable)

  suspend fun insertTimetableCell(cellList: List<TimetableCell>)

  suspend fun deleteTimetableCell(cell: TimetableCell)

  suspend fun updateTimetableCell(cell: TimetableCell)
}
