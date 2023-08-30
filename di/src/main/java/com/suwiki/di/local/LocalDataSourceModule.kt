package com.suwiki.di.local

import com.suwiki.data.datasource.local.LocalAccessTokenProviderDataSource
import com.suwiki.data.datasource.local.LocalOpenMajorProviderDataSource
import com.suwiki.data.datasource.local.LocalOpenMajorStorageDataSource
import com.suwiki.data.datasource.local.LocalRefreshTokenProviderDataSource
import com.suwiki.data.datasource.local.LocalTimetableDatasource
import com.suwiki.data.datasource.local.LocalTokenStorageDataSource
import com.suwiki.data.datasource.local.LocalUserDataSource
import com.suwiki.local.datasource.LocalAccessTokenProviderDataSourceImpl
import com.suwiki.local.datasource.LocalOpenMajorProviderDataSourceImpl
import com.suwiki.local.datasource.LocalOpenMajorStorageDataSourceImpl
import com.suwiki.local.datasource.LocalRefreshTokenProviderDataSourceImpl
import com.suwiki.local.datasource.LocalTimetableDatasourceImpl
import com.suwiki.local.datasource.LocalTokenStorageDataSourceImpl
import com.suwiki.local.datasource.LocalUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalAccessTokenProviderDataSource(
        localAccessTokenProviderDataSourceImpl: LocalAccessTokenProviderDataSourceImpl,
    ): LocalAccessTokenProviderDataSource

    @Binds
    abstract fun provideLocalRefreshTokenProviderDataSource(
        localRefreshTokenProviderDataSourceImpl: LocalRefreshTokenProviderDataSourceImpl,
    ): LocalRefreshTokenProviderDataSource

    @Binds
    abstract fun provideLocalTokenStorageDataSource(
        localTokenStorageDataSourceImpl: LocalTokenStorageDataSourceImpl,
    ): LocalTokenStorageDataSource

    @Binds
    abstract fun provideLocalUserDataSource(
        localUserDataSourceImpl: LocalUserDataSourceImpl,
    ): LocalUserDataSource

    @Binds
    abstract fun provideLocalOpenMajorProviderDataSource(
        localOpenMajorProviderDataSourceImpl: LocalOpenMajorProviderDataSourceImpl,
    ): LocalOpenMajorProviderDataSource

    @Binds
    abstract fun provideLocalOpenMajorStorageDataSource(
        localOpenMajorStorageDataSourceImpl: LocalOpenMajorStorageDataSourceImpl,
    ): LocalOpenMajorStorageDataSource

    @Binds
    abstract fun provideLocalTimetableDatasource(
        localTimetableDatasourceImpl: LocalTimetableDatasourceImpl,
    ): LocalTimetableDatasource
}
