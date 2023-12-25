package com.suwiki.local.login.di

import com.suwiki.data.login.datasource.LocalLoginDataSource
import com.suwiki.local.login.datasource.LocalLoginDataSourceImpl
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
  abstract fun bindLocalLoginDataSource(
    localLoginDataSourceImpl: LocalLoginDataSourceImpl,
  ): LocalLoginDataSource
}
