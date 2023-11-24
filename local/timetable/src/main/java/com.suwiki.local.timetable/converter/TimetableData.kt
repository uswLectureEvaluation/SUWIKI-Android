package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.TimetableEntity
import com.suwiki.core.model.timetable.Timetable

fun Timetable.toEntity(): TimetableEntity {
  return TimetableEntity(
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
}
