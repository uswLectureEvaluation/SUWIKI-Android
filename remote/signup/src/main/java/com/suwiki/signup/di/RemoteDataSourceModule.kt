package com.suwiki.signup.di

import com.suwiki.data.datasource.remote.RemoteSignUpDataSource
import com.suwiki.signup.datasource.RemoteSignUpDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemoteSignUpDatasource(
        remoteSignUpDataSourceImpl: RemoteSignUpDataSourceImpl,
    ): RemoteSignUpDataSource
}