package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.OpenLectureEntity
import com.suwiki.core.model.timetable.OpenLecture

fun OpenLecture.toEntity(): OpenLectureEntity {
  return OpenLectureEntity(
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
