package com.suwiki.openmajor.di

import com.suwiki.data.datasource.local.LocalOpenMajorProviderDataSource
import com.suwiki.data.datasource.local.LocalOpenMajorStorageDataSource
import com.suwiki.openmajor.datasource.LocalOpenMajorProviderDataSourceImpl
import com.suwiki.openmajor.datasource.LocalOpenMajorStorageDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalOpenMajorProviderDataSource(
        localOpenMajorProviderDataSourceImpl: LocalOpenMajorProviderDataSourceImpl,
    ): LocalOpenMajorProviderDataSource

    @Binds
    abstract fun bindLocalOpenMajorStorageDataSource(
        localOpenMajorStorageDataSourceImpl: LocalOpenMajorStorageDataSourceImpl,
    ): LocalOpenMajorStorageDataSource
}
