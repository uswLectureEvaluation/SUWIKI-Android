package com.suwiki.core.database

import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.suwiki.core.database.dao.OpenMajorDao
import com.suwiki.core.database.model.OpenMajorEntity

@Database(
  entities = [OpenMajorEntity::class],
  version = 1,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2, spec = OpenMajorDatabase.RenameTableAutoMigration::class),
//    ],
  exportSchema = true,
)
abstract class OpenMajorDatabase : RoomDatabase() {
  abstract fun openMajorDao(): OpenMajorDao

  @RenameTable(fromTableName = "OpenMajorData", toTableName = "OpenMajorEntity")
  class RenameTableAutoMigration : AutoMigrationSpec
}
