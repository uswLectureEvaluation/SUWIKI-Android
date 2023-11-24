package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.OpenLectureEntity
import com.suwiki.core.model.timetable.OpenLecture

fun OpenLectureEntity.toModel() = OpenLecture(
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
