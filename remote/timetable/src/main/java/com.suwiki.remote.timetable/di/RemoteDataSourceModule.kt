package com.suwiki.remote.timetable.di

import com.suwiki.data.timetable.datasource.RemoteOpenLectureDataSource
import com.suwiki.remote.timetable.datasource.RemoteOpenLectureDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

  @Singleton
  @Binds
  abstract fun bindRemoteTimetableDataSource(
    remoteTimetableDataSourceImpl: RemoteOpenLectureDataSourceImpl,
  ): RemoteOpenLectureDataSource
}
