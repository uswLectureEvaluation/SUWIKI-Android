package com.suwiki.local.token.storage.di

import com.suwiki.data.datasource.local.LocalTokenStorageDataSource
import com.suwiki.local.token.storage.datasource.LocalTokenStorageDataSourceImpl
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
    abstract fun bindLocalTokenStorageDataSource(
        localTokenStorageDataSourceImpl: LocalTokenStorageDataSourceImpl,
    ): LocalTokenStorageDataSource
}
