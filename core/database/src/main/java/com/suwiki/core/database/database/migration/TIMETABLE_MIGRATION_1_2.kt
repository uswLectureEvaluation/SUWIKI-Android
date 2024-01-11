package com.suwiki.core.database.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal val TIMETABLE_MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL(
      """
      CREATE TABLE IF NOT EXISTS TimetableEntity (
      `createTime` INTEGER NOT NULL,
      `year` TEXT NOT NULL,
      `semester` TEXT NOT NULL,
      `name` TEXT NOT NULL,
      `cellList` TEXT NOT NULL,
      PRIMARY KEY(`createTime`)
      )
      """.trimIndent(),
    )

    val cursor = database.query("SELECT * FROM TimetableList")

    if (cursor.moveToFirst()) {
      do {
        val createTime = cursor.getLong(cursor.getColumnIndexOrThrow("createTime"))
        val year = cursor.getString(cursor.getColumnIndexOrThrow("year"))
        val semester = cursor.getString(cursor.getColumnIndexOrThrow("semester"))
        val timeTableName = cursor.getString(cursor.getColumnIndexOrThrow("timeTableName"))
        val timeTableJsonData = cursor.getString(cursor.getColumnIndexOrThrow("timeTableJsonData"))

        val cellList = timeTableJsonDataToCellList(timeTableJsonData)

        database.execSQL(
          """
          INSERT INTO TimetableEntity (createTime, year, semester, name, cellList)
          VALUES ($createTime, '$year', '$semester', '$timeTableName', '$cellList')
          """.trimIndent(),
        )
      } while (cursor.moveToNext())
    }

    cursor.close()
    database.execSQL("DROP TABLE TimeTableList")
  }
}

@Serializable
data class LegacyTimeTableCell(
  val name: String = "",
  val professor: String = "",
  val location: String = "",
  val day: String = "",
  val startTime: String = "",
  val endTime: String = "",
  val color: Int,
  val credit: String = "",
)

fun LegacyTimeTableCell.toTimetableCell(): TimetableCell {
  val color = when (this.color) {
    -96120 -> TimetableCellColor.PINK
    -16046 -> TimetableCellColor.ORANGE
    -3368205 -> TimetableCellColor.VIOLET
    -7747330 -> TimetableCellColor.SKY
    -5907327 -> TimetableCellColor.GREEN
    -4026526 -> TimetableCellColor.BROWN
    -4013635 -> TimetableCellColor.GRAY
    -12363882 -> TimetableCellColor.NAVY
    -9728172 -> TimetableCellColor.GREEN_DARK
    -17536 -> TimetableCellColor.BROWN_LIGHT
    -6194752 -> TimetableCellColor.PURPLE
    -7369077 -> TimetableCellColor.GRAY_DARK
    else -> TimetableCellColor.entries.shuffled()[0]
  }

  val day = when (this.day) {
    "월" -> TimetableDay.MON
    "화" -> TimetableDay.TUE
    "수" -> TimetableDay.WED
    "목" -> TimetableDay.THU
    "금" -> TimetableDay.FRI
    "토" -> TimetableDay.SAT
    "이러닝" -> TimetableDay.E_LEARNING
    else -> TimetableDay.E_LEARNING
  }

  return TimetableCell(
    name = name,
    professor = professor,
    location = location,
    day = day,
    startPeriod = startTime.toIntOrNull() ?: 0,
    endPeriod = endTime.toIntOrNull() ?: 0,
    color = color,
  )
}


fun timeTableJsonDataToCellList(timeTableJsonData: String): String {
  if (timeTableJsonData.isBlank()) return ""
  val legacy = Json.decodeFromString<List<LegacyTimeTableCell>>(timeTableJsonData)
  val cellList = legacy.map { it.toTimetableCell() }
  return Json.encodeToString(cellList)
}
