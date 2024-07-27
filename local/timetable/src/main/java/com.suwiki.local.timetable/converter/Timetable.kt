package com.suwiki.local.timetable.converter

import com.suwiki.common.model.timetable.Timetable
import com.suwiki.local.common.database.entity.TimetableEntity

fun Timetable.toEntity() = TimetableEntity(
  createTime = createTime,
  year = year,
  semester = semester,
  name = name,
  cellList = cellList,
)
