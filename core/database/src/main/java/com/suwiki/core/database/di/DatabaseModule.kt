package com.suwiki.core.database.di

import android.content.Context
import androidx.room.Room
import com.suwiki.core.database.OpenMajorDatabase
import com.suwiki.core.database.OpenLectureDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
  fun provideOpenLectureDatabase(
    @ApplicationContext context: Context,
  ): OpenLectureDatabase {
    return Room.databaseBuilder(
      context,
      OpenLectureDatabase::class.java,
      "open-lecture-database",
    ).build()
  }
}
