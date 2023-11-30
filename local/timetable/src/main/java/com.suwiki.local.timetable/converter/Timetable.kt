package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.TimetableEntity
import com.suwiki.core.model.timetable.Timetable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Timetable.toEntity() = TimetableEntity(
  createTime = createTime,
  year = year,
  semester = semester,
  timetableName = name,
  timetableJsonData = Json.encodeToString(cellList),
)
