package com.suwiki.lectureevaluation.viewer.di

import com.suwiki.data.datasource.remote.RemoteExamProviderDataSource
import com.suwiki.data.datasource.remote.RemoteLectureProviderDataSource
import com.suwiki.lectureevaluation.viewer.datasource.RemoteExamProviderDataSourceImpl
import com.suwiki.lectureevaluation.viewer.datasource.RemoteLectureProviderDataSourceImpl
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
    abstract fun bindRemoteLectureProviderDatasource(
        remoteLectureProviderDataSourceImpl: RemoteLectureProviderDataSourceImpl,
    ): RemoteLectureProviderDataSource

    @Singleton
    @Binds
    abstract fun bindRemoteExamProviderDatasource(
        remoteExamProviderDataSourceImpl: RemoteExamProviderDataSourceImpl,
    ): RemoteExamProviderDataSource
}