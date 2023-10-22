package com.suwiki.local.openmajor.di

import com.suwiki.data.openmajor.datasource.LocalOpenMajorProviderDataSource
import com.suwiki.data.openmajor.datasource.LocalOpenMajorStorageDataSource
import com.suwiki.local.openmajor.datasource.LocalOpenMajorProviderDataSourceImpl
import com.suwiki.local.openmajor.datasource.LocalOpenMajorStorageDataSourceImpl
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
  abstract fun bindLocalOpenMajorProviderDataSource(
    localOpenMajorProviderDataSourceImpl: LocalOpenMajorProviderDataSourceImpl,
  ): LocalOpenMajorProviderDataSource

  @Singleton
  @Binds
  abstract fun bindLocalOpenMajorStorageDataSource(
    localOpenMajorStorageDataSourceImpl: LocalOpenMajorStorageDataSourceImpl,
  ): LocalOpenMajorStorageDataSource
}
