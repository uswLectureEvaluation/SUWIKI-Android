package com.suwiki.di.local

import com.suwiki.local.db.OpenMajorDatabase
import com.suwiki.local.db.TimetableDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideOpenMajorDao(db: OpenMajorDatabase) = db.openMajorDao()

    @Provides
    fun provideTimetableDao(db: TimetableDatabase) = db.timetableDao()
}
