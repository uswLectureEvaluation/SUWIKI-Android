package com.suwiki.di.local

import com.suwiki.data.datasource.local.LocalAuthDataSource
import com.suwiki.data.datasource.local.LocalOpenMajorDataSource
import com.suwiki.data.datasource.local.LocalTimetableDatasource
import com.suwiki.local.datasource.LocalAuthDataSourceImpl
import com.suwiki.local.datasource.LocalOpenMajorDataSourceImpl
import com.suwiki.local.datasource.LocalTimetableDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalAuthDataSource(
        localAuthDataSourceImpl: LocalAuthDataSourceImpl,
    ): LocalAuthDataSource

    @Binds
    abstract fun provideLocalOpenMajorDataSource(
        localOpenMajorDataSourceImpl: LocalOpenMajorDataSourceImpl,
    ): LocalOpenMajorDataSource

    @Binds
    abstract fun provideLocalTimetableDatasource(
        localTimetableDatasourceImpl: LocalTimetableDatasourceImpl,
    ): LocalTimetableDatasource
}
