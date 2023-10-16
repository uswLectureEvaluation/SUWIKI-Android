package com.suwiki.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suwiki.database.dao.TimetableDao
import com.suwiki.database.model.TimetableEntity

@Database(entities = [TimetableEntity::class], version = 1)
abstract class TimetableDatabase : RoomDatabase() {
    abstract fun timetableDao(): TimetableDao
}
