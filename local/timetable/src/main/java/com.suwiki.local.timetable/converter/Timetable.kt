package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.TimetableEntity
import com.suwiki.core.model.timetable.Timetable

fun TimetableEntity.toModel() = Timetable(
  number,
  major,
  grade,
  classNumber,
  classDivideNumber,
  className,
  classification,
  professor,
  time,
  credit,
)
