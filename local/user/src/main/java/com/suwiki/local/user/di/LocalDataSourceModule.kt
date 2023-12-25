package com.suwiki.local.user.di

import com.suwiki.data.user.datasource.LocalUserDataSource
import com.suwiki.local.user.datasource.LocalUserDataSourceImpl
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
    localUserProviderDataSourceImpl: LocalUserDataSourceImpl,
  ): LocalUserDataSource
}
