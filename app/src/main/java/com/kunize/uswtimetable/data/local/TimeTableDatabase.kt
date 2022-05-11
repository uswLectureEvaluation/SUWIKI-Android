package com.kunize.uswtimetable.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimeTableData::class],version = 1)
abstract class TimeTableDatabase : RoomDatabase() {
    abstract fun timetableDao(): TimeTableDao

    companion object {
        private var instance: TimeTableDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TimeTableDatabase? {
            if (instance == null) {
                synchronized(TimeTableDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimeTableDatabase::class.java,
                        "timetable-database"
                    ).build()
                }
            }
            return instance
        }
    }
}