package com.suwiki.data.db.entity.converter

import com.suwiki.data.db.entity.TimetableEntity
import com.suwiki.domain.model.TimetableData

fun TimetableData.toEntity(): TimetableEntity {
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

fun TimetableEntity.toDomain(): TimetableData {
  return TimetableData(
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
