package com.kunize.uswtimetable.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.suwiki.data.db.OpenMajorDatabase
import com.suwiki.data.db.TimetableDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
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

    @Singleton
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
}
