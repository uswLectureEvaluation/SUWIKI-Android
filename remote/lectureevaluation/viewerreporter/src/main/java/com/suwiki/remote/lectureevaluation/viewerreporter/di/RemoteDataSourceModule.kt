package com.suwiki.remote.lectureevaluation.viewerreporter.di

import com.suwiki.data.lectureevaluation.report.datasource.RemoteExamReportDataSource
import com.suwiki.data.lectureevaluation.report.datasource.RemoteLectureReportDataSource
import com.suwiki.data.lectureevaluation.viewer.datasource.RemoteExamProviderDataSource
import com.suwiki.data.lectureevaluation.viewer.datasource.RemoteLectureProviderDataSource
import com.suwiki.remote.lectureevaluation.viewerreporter.datasource.RemoteExamProviderDataSourceImpl
import com.suwiki.remote.lectureevaluation.viewerreporter.datasource.RemoteExamReportDataSourceImpl
import com.suwiki.remote.lectureevaluation.viewerreporter.datasource.RemoteLectureProviderDataSourceImpl
import com.suwiki.remote.lectureevaluation.viewerreporter.datasource.RemoteLectureReportDataSourceImpl
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

  @Singleton
  @Binds
  abstract fun bindRemoteExamReportDatasource(
    remoteOpenMajorDataSourceImpl: RemoteExamReportDataSourceImpl,
  ): RemoteExamReportDataSource

  @Singleton
  @Binds
  abstract fun bindLectureExamReportDatasource(
    remoteOpenMajorDataSourceImpl: RemoteLectureReportDataSourceImpl,
  ): RemoteLectureReportDataSource
}
