package com.suwiki.core.database.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.suwiki.core.database.dao.OpenLectureDao
import com.suwiki.core.database.model.OpenLectureEntity

@Database(
  entities = [OpenLectureEntity::class],
  version = 1,
  autoMigrations = [
    AutoMigration(from = 1, to = 2, spec = OpenLectureDatabase.RenameTableAutoMigration::class),
  ],
  exportSchema = true,
)
abstract class OpenLectureDatabase : RoomDatabase() {
  abstract fun openLectureDao(): OpenLectureDao

  @RenameTable(fromTableName = "TimetableEntity", toTableName = "OpenLectureEntity")
  class RenameTableAutoMigration : AutoMigrationSpec
}
