package com.suwiki.core.database.di

import com.suwiki.core.database.OpenMajorDatabase
import com.suwiki.core.database.OpenLectureDatabase
import com.suwiki.core.database.dao.OpenMajorDao
import com.suwiki.core.database.dao.OpenLectureDao
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
  fun provideOpenLectureDao(db: OpenLectureDatabase): OpenLectureDao = db.openLectureDao()
}
