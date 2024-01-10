package com.suwiki.core.database.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.suwiki.core.database.dao.TimeTableDao
import com.suwiki.core.database.di.DatabaseName
import com.suwiki.core.database.model.TimetableEntity

@Database(
  entities = [TimetableEntity::class],
  version = 2,
)
abstract class TimetableDatabase : RoomDatabase() {
  abstract fun timetableDao(): TimeTableDao
}
