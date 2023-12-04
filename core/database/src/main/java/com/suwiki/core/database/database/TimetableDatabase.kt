package com.suwiki.core.database.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.suwiki.core.database.dao.TimeTableDao
import com.suwiki.core.database.model.TimetableEntity

@Database(
  entities = [TimetableEntity::class],
  version = 2,
  autoMigrations = [
    AutoMigration(from = 1, to = 2, spec = TimetableDatabase.RenameTableAutoMigration::class),
  ],
  exportSchema = true,
)
abstract class TimetableDatabase : RoomDatabase() {
  abstract fun timetableDao(): TimeTableDao

  @RenameTable(fromTableName = "TimeTableList", toTableName = "TimetableEntity")
  class RenameTableAutoMigration : AutoMigrationSpec
}