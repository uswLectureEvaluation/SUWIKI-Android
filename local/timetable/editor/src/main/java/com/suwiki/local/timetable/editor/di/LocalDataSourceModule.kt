package com.suwiki.local.timetable.editor.di

import com.suwiki.data.timetable.editor.datasource.LocalTimetableStorageDatasource
import com.suwiki.local.timetable.editor.datasource.LocalTimetableStorageDatasourceImpl
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
}
