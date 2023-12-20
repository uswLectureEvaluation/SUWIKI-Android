package com.suwiki.remote.login.di

import com.suwiki.data.login.datasource.RemoteLoginDataSource
import com.suwiki.remote.login.datasource.RemoteLoginDataSourceImpl
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
  abstract fun bindRemoteLoginDatasource(
    remoteLoginDataSourceImpl: RemoteLoginDataSourceImpl,
  ): RemoteLoginDataSource
}
