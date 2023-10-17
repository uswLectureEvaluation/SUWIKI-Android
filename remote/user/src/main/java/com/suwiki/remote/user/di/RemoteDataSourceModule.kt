package com.suwiki.remote.user.di

import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.remote.user.datasource.RemoteUserDataSourceImpl
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
    abstract fun bindRemoteUserDatasource(
        remoteUserDataSourceImpl: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource
}
