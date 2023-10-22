package com.suwiki.remote.timetable.viewer.di

import com.suwiki.data.timetable.viewer.datasource.RemoteTimetableDataSource
import com.suwiki.remote.timetable.viewer.datasource.RemoteTimetableDataSourceImpl
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
    remoteTimetableDataSourceImpl: RemoteTimetableDataSourceImpl,
  ): RemoteTimetableDataSource
}
