package com.suwiki.local.timetable.viewer.converter

import com.suwiki.core.database.model.TimetableEntity
import com.suwiki.core.model.TimetableData

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