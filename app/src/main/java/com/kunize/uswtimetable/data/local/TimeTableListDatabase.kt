package com.kunize.uswtimetable.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimeTableList::class],version = 1, exportSchema = true)
abstract class TimeTableListDatabase : RoomDatabase() {
    abstract fun timetableListDao(): TimeTableListDao

    companion object {
        private var instance: TimeTableListDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TimeTableListDatabase? {
            if (instance == null) {
                synchronized(TimeTableListDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimeTableListDatabase::class.java,
                        "timetable-list-database"
                    ).build()
                }
            }
            return instance
        }
    }
}