package com.suwiki.local.common.database.converter

import androidx.room.TypeConverter
import com.suwiki.core.model.timetable.TimetableCell
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TimetableCellListConverter {
  @TypeConverter
  fun cellListToJson(value: List<TimetableCell>): String {
    return Json.encodeToString(value)
  }

  @TypeConverter
  fun jsonToCellList(value: String): List<TimetableCell> {
    return if (value.isBlank()) {
      emptyList()
    } else {
      Json.decodeFromString(value)
    }
  }
}
