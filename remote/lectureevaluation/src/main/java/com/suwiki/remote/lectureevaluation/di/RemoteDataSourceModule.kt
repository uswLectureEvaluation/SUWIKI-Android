package com.suwiki.remote.lectureevaluation.di

import com.suwiki.data.lectureevaluation.datasource.RemoteExamEditorDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteExamMyDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteExamProviderDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteExamReportDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureEditorDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureMyDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureProviderDataSource
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureReportDataSource
import com.suwiki.remote.lectureevaluation.datasource.RemoteExamEditorDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteExamMyDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteExamProviderDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteExamReportDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteLectureEditorDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteLectureMyDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteLectureProviderDataSourceImpl
import com.suwiki.remote.lectureevaluation.datasource.RemoteLectureReportDataSourceImpl
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
  abstract fun bindRemoteLectureEditorDatasource(
    remoteLectureEditorDataSourceImpl: RemoteLectureEditorDataSourceImpl,
  ): RemoteLectureEditorDataSource

  @Singleton
  @Binds
  abstract fun bindRemoteExamEditorDatasource(
    remoteExamEditorDataSourceImpl: RemoteExamEditorDataSourceImpl,
  ): RemoteExamEditorDataSource

  @Singleton
  @Binds
  abstract fun bindRemoteExamMyDatasource(
    remoteOpenMajorDataSourceImpl: RemoteExamMyDataSourceImpl,
  ): RemoteExamMyDataSource

  @Singleton
  @Binds
  abstract fun bindLectureExamMyDatasource(
    remoteOpenMajorDataSourceImpl: RemoteLectureMyDataSourceImpl,
  ): RemoteLectureMyDataSource

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
