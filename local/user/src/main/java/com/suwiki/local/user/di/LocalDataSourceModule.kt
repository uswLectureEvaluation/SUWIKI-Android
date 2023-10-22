package com.suwiki.local.user.di

import com.suwiki.data.user.datasource.LocalUserProviderDataSource
import com.suwiki.data.user.datasource.LocalUserStorageDataSource
import com.suwiki.local.user.datasource.LocalUserProviderDataSourceImpl
import com.suwiki.local.user.datasource.LocalUserStorageDataSourceImpl
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
  abstract fun bindLocalUserProviderDataSource(
    localUserProviderDataSourceImpl: LocalUserProviderDataSourceImpl,
  ): LocalUserProviderDataSource

  @Singleton
  @Binds
  abstract fun bindLocalUserStorageDataSource(
    localUserStorageDataSourceImpl: LocalUserStorageDataSourceImpl,
  ): LocalUserStorageDataSource
}
