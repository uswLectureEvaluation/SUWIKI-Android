package com.suwiki.local.timetable.di

import com.suwiki.data.timetable.datasource.LocalOpenLectureProviderDatasource
import com.suwiki.data.timetable.datasource.LocalOpenLectureStorageDatasource
import com.suwiki.data.timetable.datasource.LocalTimetableDataSource
import com.suwiki.local.timetable.datasource.LocalOpenLectureProviderDatasourceImpl
import com.suwiki.local.timetable.datasource.LocalOpenLectureStorageDatasourceImpl
import com.suwiki.local.timetable.datasource.LocalTimetableDatasourceImpl
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
  abstract fun bindLocalOpenLectureStorageDataSource(
    localOpenLectureStorageDataSourceImpl: LocalOpenLectureStorageDatasourceImpl,
  ): LocalOpenLectureStorageDatasource

  @Singleton
  @Binds
  abstract fun bindLocalOpenLectureProviderDataSource(
    localOpenLectureProviderDataSourceImpl: LocalOpenLectureProviderDatasourceImpl,
  ): LocalOpenLectureProviderDatasource

  @Singleton
  @Binds
  abstract fun bindLocalTimetableDataSource(
    localTimetableDatasourceImpl: LocalTimetableDatasourceImpl,
  ): LocalTimetableDataSource
}
