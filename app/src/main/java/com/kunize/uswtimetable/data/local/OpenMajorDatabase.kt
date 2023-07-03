package com.kunize.uswtimetable.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OpenMajorData::class], version = 1, exportSchema = true)
abstract class OpenMajorDatabase : RoomDatabase() {
    abstract fun openMajorDao(): OpenMajorDao

    companion object {
        private var instance: OpenMajorDatabase? = null

        @Synchronized
        fun getInstance(context: Context): OpenMajorDatabase? {
            if (instance == null) {
                synchronized(OpenMajorDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OpenMajorDatabase::class.java,
                        "open-major-database",
                    ).build()
                }
            }
            return instance
        }
    }
}
