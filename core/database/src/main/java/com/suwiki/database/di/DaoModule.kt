package com.suwiki.database.di

import com.suwiki.database.OpenMajorDatabase
import com.suwiki.database.TimetableDatabase
import com.suwiki.database.dao.OpenMajorDao
import com.suwiki.database.dao.TimetableDao
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
    fun provideTimetableDao(db: TimetableDatabase): TimetableDao = db.timetableDao()
}
