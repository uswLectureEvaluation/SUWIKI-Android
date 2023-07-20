package com.suwiki.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suwiki.data.db.dao.TimetableDao
import com.suwiki.data.db.entity.TimetableEntity

@Database(entities = [TimetableEntity::class], version = 1)
abstract class TimetableDatabase : RoomDatabase() {
    abstract fun timetableDao(): TimetableDao
}
