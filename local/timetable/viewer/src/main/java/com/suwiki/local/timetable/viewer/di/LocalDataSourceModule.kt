package com.suwiki.local.timetable.viewer.di

import com.suwiki.data.timetable.viewer.datasource.LocalTimetableProviderDatasource
import com.suwiki.local.timetable.viewer.datasource.LocalTimetableProviderDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

  @Singleton
  @Binds
  abstract fun bindLocalTimetableProviderDataSource(
    localTimetableProviderDataSourceImpl: LocalTimetableProviderDatasourceImpl,
  ): LocalTimetableProviderDatasource
}
