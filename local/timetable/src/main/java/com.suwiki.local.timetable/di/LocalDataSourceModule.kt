package com.suwiki.local.timetable.di

import com.suwiki.data.timetable.editor.datasource.LocalTimetableStorageDatasource
import com.suwiki.data.timetable.viewer.datasource.LocalTimetableProviderDatasource
import com.suwiki.local.timetable.datasource.LocalTimetableProviderDatasourceImpl
import com.suwiki.local.timetable.datasource.LocalTimetableStorageDatasourceImpl
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
  abstract fun bindLocalTimetableStorageDataSource(
    localTimetableStorageDataSourceImpl: LocalTimetableStorageDatasourceImpl,
  ): LocalTimetableStorageDatasource

  @Singleton
  @Binds
  abstract fun bindLocalTimetableProviderDataSource(
    localTimetableProviderDataSourceImpl: LocalTimetableProviderDatasourceImpl,
  ): LocalTimetableProviderDatasource
}
