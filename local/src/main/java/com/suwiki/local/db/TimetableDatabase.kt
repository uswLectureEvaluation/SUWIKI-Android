package com.suwiki.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suwiki.local.db.dao.TimetableDao
import com.suwiki.local.model.TimetableEntity

@Database(entities = [TimetableEntity::class], version = 1)
abstract class TimetableDatabase : RoomDatabase() {
    abstract fun timetableDao(): TimetableDao
}
