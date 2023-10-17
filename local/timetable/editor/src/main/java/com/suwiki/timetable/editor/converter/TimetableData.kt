package com.suwiki.timetable.editor.converter

import com.suwiki.database.model.TimetableEntity
import com.suwiki.model.TimetableData

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
