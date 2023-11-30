package com.suwiki.local.timetable.converter

import com.suwiki.core.database.model.TimetableEntity
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun TimetableEntity.toModel() = Timetable(
  createTime = createTime,
  year = year,
  semester = semester,
  name = timetableName,
  cellList = Json.decodeFromString(timetableJsonData),
)

fun arrayToJson(data: MutableList<TimetableCell>): String {
  return Json.encodeToString(data)
}
