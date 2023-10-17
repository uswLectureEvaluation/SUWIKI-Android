package com.suwiki.timetable.viewer.di

import com.suwiki.data.datasource.local.LocalTimetableProviderDatasource
import com.suwiki.timetable.viewer.datasource.LocalTimetableProviderDatasourceImpl
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
