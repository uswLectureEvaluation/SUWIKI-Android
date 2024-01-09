package com.suwiki.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suwiki.core.database.dao.TimeTableDao
import com.suwiki.core.database.model.TimetableEntity

@Database(
  entities = [TimetableEntity::class],
  version = 1,
)
abstract class TimetableDatabase : RoomDatabase() {
  abstract fun timetableDao(): TimeTableDao
}
