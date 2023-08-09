package com.suwiki.di.local

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.suwiki.local.db.OpenMajorDatabase
import com.suwiki.local.db.TimetableDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideOpenMajorDatabase(
        @ApplicationContext context: Context,
    ): OpenMajorDatabase {
        return Room.databaseBuilder(
            context,
            OpenMajorDatabase::class.java,
            "open-major-database",
        ).build()
    }

    @Provides
    fun provideTimetableDatabase(
        @ApplicationContext context: Context,
    ): TimetableDatabase {
        return Room.databaseBuilder(
            context,
            TimetableDatabase::class.java,
            "timetable-database",
        ).build()
    }

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }
}
