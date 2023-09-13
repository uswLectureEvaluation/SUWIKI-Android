package com.suwiki.di.local

import com.suwiki.data.datasource.local.LocalAccessTokenProviderDataSource
import com.suwiki.data.datasource.local.LocalOpenMajorProviderDataSource
import com.suwiki.data.datasource.local.LocalOpenMajorStorageDataSource
import com.suwiki.data.datasource.local.LocalRefreshTokenProviderDataSource
import com.suwiki.data.datasource.local.LocalTimetableProviderDatasource
import com.suwiki.data.datasource.local.LocalTimetableStorageDatasource
import com.suwiki.data.datasource.local.LocalTokenStorageDataSource
import com.suwiki.data.datasource.local.LocalUserProviderDataSource
import com.suwiki.data.datasource.local.LocalUserStorageDataSource
import com.suwiki.local.datasource.LocalAccessTokenProviderDataSourceImpl
import com.suwiki.local.datasource.LocalOpenMajorProviderDataSourceImpl
import com.suwiki.local.datasource.LocalOpenMajorStorageDataSourceImpl
import com.suwiki.local.datasource.LocalRefreshTokenProviderDataSourceImpl
import com.suwiki.local.datasource.LocalTimetableProviderDatasourceImpl
import com.suwiki.local.datasource.LocalTimetableStorageDatasourceImpl
import com.suwiki.local.datasource.LocalTokenStorageDataSourceImpl
import com.suwiki.local.datasource.LocalUserProviderDataSourceImpl
import com.suwiki.local.datasource.LocalUserStorageDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalAccessTokenProviderDataSource(
        localAccessTokenProviderDataSourceImpl: LocalAccessTokenProviderDataSourceImpl,
    ): LocalAccessTokenProviderDataSource

    @Binds
    abstract fun bindLocalRefreshTokenProviderDataSource(
        localRefreshTokenProviderDataSourceImpl: LocalRefreshTokenProviderDataSourceImpl,
    ): LocalRefreshTokenProviderDataSource

    @Binds
    abstract fun bindLocalTokenStorageDataSource(
        localTokenStorageDataSourceImpl: LocalTokenStorageDataSourceImpl,
    ): LocalTokenStorageDataSource

    @Binds
    abstract fun bindLocalUserProviderDataSource(
        localUserProviderDataSourceImpl: LocalUserProviderDataSourceImpl,
    ): LocalUserProviderDataSource

    @Binds
    abstract fun bindLocalUserStorageDataSource(
        localUserStorageDataSourceImpl: LocalUserStorageDataSourceImpl,
    ): LocalUserStorageDataSource

    @Binds
    abstract fun bindLocalOpenMajorProviderDataSource(
        localOpenMajorProviderDataSourceImpl: LocalOpenMajorProviderDataSourceImpl,
    ): LocalOpenMajorProviderDataSource

    @Binds
    abstract fun bindLocalOpenMajorStorageDataSource(
        localOpenMajorStorageDataSourceImpl: LocalOpenMajorStorageDataSourceImpl,
    ): LocalOpenMajorStorageDataSource

    @Binds
    abstract fun bindLocalTimetableProviderDatasource(
        localTimetableProviderDatasourceImpl: LocalTimetableProviderDatasourceImpl,
    ): LocalTimetableProviderDatasource

    @Binds
    abstract fun bindLocalTimetableStorageDatasource(
        localTimetableStorageDatasourceImpl: LocalTimetableStorageDatasourceImpl,
    ): LocalTimetableStorageDatasource
}
