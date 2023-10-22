package com.suwiki.remote.lectureevaluation.viewer.di

import com.suwiki.data.lectureevaluation.viewer.datasource.RemoteExamProviderDataSource
import com.suwiki.data.lectureevaluation.viewer.datasource.RemoteLectureProviderDataSource
import com.suwiki.remote.lectureevaluation.viewer.datasource.RemoteExamProviderDataSourceImpl
import com.suwiki.remote.lectureevaluation.viewer.datasource.RemoteLectureProviderDataSourceImpl
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
