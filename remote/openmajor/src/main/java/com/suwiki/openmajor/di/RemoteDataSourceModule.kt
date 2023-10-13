package com.suwiki.openmajor.di

import com.suwiki.data.datasource.remote.RemoteOpenMajorDataSource
import com.suwiki.openmajor.datasource.RemoteOpenMajorDataSourceImpl
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
    abstract fun bindRemoteOpenMajorDatasource(
        remoteOpenMajorDataSourceImpl: RemoteOpenMajorDataSourceImpl,
    ): RemoteOpenMajorDataSource
}