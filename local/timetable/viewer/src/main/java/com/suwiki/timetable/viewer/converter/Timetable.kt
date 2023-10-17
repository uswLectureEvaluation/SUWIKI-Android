package com.suwiki.timetable.viewer.converter

import com.suwiki.database.model.TimetableEntity
import com.suwiki.model.TimetableData

fun TimetableEntity.toModel() = TimetableData(
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