package com.suwiki.remote.timetable.editor.di

import com.suwiki.data.datasource.remote.RemoteOpenMajorDataSource
import com.suwiki.data.datasource.remote.RemoteTimetableDataSource
import com.suwiki.remote.timetable.editor.datasource.RemoteTimetableDataSourceImpl
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