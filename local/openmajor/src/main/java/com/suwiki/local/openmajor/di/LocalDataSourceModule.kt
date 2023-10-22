package com.suwiki.local.openmajor.di

import com.suwiki.data.openmajor.datasource.LocalOpenMajorDataSource
import com.suwiki.local.openmajor.datasource.LocalOpenMajorDataSourceImpl
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
  abstract fun bindLocalOpenMajorDataSource(
    localOpenMajorDataSourceImpl: LocalOpenMajorDataSourceImpl,
  ): LocalOpenMajorDataSource
}
