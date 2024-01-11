package com.suwiki.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suwiki.core.database.dao.TimeTableDao
import com.suwiki.core.database.database.converter.TimetableCellListConverter
import com.suwiki.core.database.model.TimetableEntity

@Database(
  entities = [TimetableEntity::class],
  version = 2,
)
@TypeConverters(
  value = [TimetableCellListConverter::class],
)
abstract class TimetableDatabase : RoomDatabase() {
  abstract fun timetableDao(): TimeTableDao
}
