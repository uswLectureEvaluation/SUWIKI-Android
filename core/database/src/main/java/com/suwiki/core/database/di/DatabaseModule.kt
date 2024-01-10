package com.suwiki.core.database.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import androidx.room.TypeConverter
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suwiki.core.database.database.OpenLectureDatabase
import com.suwiki.core.database.database.OpenMajorDatabase
import com.suwiki.core.database.database.TimetableDatabase
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.Time
import javax.inject.Singleton

@ProvidedTypeConverter
class TimetableCellListConverter {
  @TypeConverter
  fun cellListToJson(value: List<TimetableCell>): String {
    return Json.encodeToString(value)
  }

  @TypeConverter
  fun jsonToCellList(value: String): List<TimetableCell> {
    return Json.decodeFromString(value)
  }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideOpenMajorDatabase(
    @ApplicationContext context: Context,
  ): OpenMajorDatabase {
    return Room
      .databaseBuilder(
        context,
        OpenMajorDatabase::class.java,
        DatabaseName.OPEN_MAJOR,
      )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun provideOpenLectureDatabase(
    @ApplicationContext context: Context,
  ): OpenLectureDatabase {
    return Room.databaseBuilder(
      context,
      OpenLectureDatabase::class.java,
      DatabaseName.OPEN_LECTURE,
    )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun provideTimetableDatabase(
    @ApplicationContext context: Context,
  ): TimetableDatabase {
    return Room
      .databaseBuilder(
        context,
        TimetableDatabase::class.java,
        DatabaseName.TIMETABLE,
      )
      .addTypeConverter(TimetableCellListConverter())
      .addMigrations(TIMETABLE_MIGRATION_1_2)
      // .fallbackToDestructiveMigration()
      .build()
  }
}

private val TIMETABLE_MIGRATION_1_2 = object : Migration(1, 2) {
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

    val cursor = database.query("TimeTableList", arrayOf("createTime", "timeTableJsonData"))

    if (cursor.moveToFirst()) {
      do {
        val createTime = cursor.getInt(cursor.getColumnIndexOrThrow("createTime"))
        val timeTableJsonData = cursor.getString(cursor.getColumnIndexOrThrow("timeTableJsonData"))

        val cellList = timeTableJsonDataToCellList(timeTableJsonData)

        val contentValues = ContentValues().apply {
          put("timeTableJsonData", cellList)
        }

        database.update(
          table = "TimetableEntity",
          conflictAlgorithm = SQLiteDatabase.CONFLICT_IGNORE,
          values = contentValues,
          whereClause = "createTime = ?",
          whereArgs = arrayOf(createTime.toString()),
        )
      } while (cursor.moveToNext())
    }


    database.execSQL(
      """
        INSERT INTO TimetableEntity (createTime, year, semester, name, cellList)
        SELECT createTime, year, semester, timeTableName, timeTableJsonData  FROM TimeTableList
      """.trimIndent(),
    )
    database.execSQL("DROP TABLE TimeTableList")
  }
}

private data class LegacyTimeTableCell(
  var name: String = "",
  var professor: String = "",
  var location: String = "",
  var day: String = "",
  var startTime: String = "",
  var endTime: String = "",
  var color: Int = -1,
  var credit: String = "",
) {
  fun toTimetableCell(): TimetableCell {
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
      credit = credit,
    )
  }
}

fun timeTableJsonDataToCellList(timeTableJsonData: String): String {
  val legacy = Json.decodeFromString<List<LegacyTimeTableCell>>(timeTableJsonData)
  val cellList = legacy.map { it.toTimetableCell() }
  return Json.encodeToString(cellList)
}

/**
 * 상수 이름과 실제 값이 일치하지 않는 이유는 이전 버전과의 호환을 위해 레거시 데이터베이스의 이름을 변경할 수 없었기 때문입니다.
 */
object DatabaseName {
  const val OPEN_MAJOR = "open-major-database"
  const val OPEN_LECTURE = "timetable-database"
  const val TIMETABLE = "timetable-list-database"
}
