package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.TimetableEntity
import com.suwiki.core.model.timetable.Timetable

fun TimetableEntity.toModel() = Timetable(
  createTime = createTime,
  year = year,
  semester = semester,
  name = name,
  cellList = cellList,
)
