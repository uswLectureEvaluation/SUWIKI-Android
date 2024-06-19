package com.suwiki.local.common.database.di

import com.suwiki.local.common.database.OpenMajorDatabase
import com.suwiki.local.common.database.TimetableDatabase
import com.suwiki.local.common.database.dao.OpenMajorDao
import com.suwiki.local.common.database.dao.TimeTableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

  @Provides
  fun provideOpenMajorDao(db: OpenMajorDatabase): OpenMajorDao = db.openMajorDao()

  @Provides
  fun provideTimetableDao(db: TimetableDatabase): TimeTableDao = db.timetableDao()
}
