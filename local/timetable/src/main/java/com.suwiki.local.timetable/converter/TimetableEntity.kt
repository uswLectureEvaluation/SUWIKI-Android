package com.suwiki.local.timetable.converter

import com.suwiki.core.model.timetable.Timetable
import com.suwiki.local.common.database.entity.TimetableEntity

fun TimetableEntity.toModel() = Timetable(
  createTime = createTime,
  year = year,
  semester = semester,
  name = name,
  cellList = cellList,
)
