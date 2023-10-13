package com.suwiki.lectureevaluation.report.di

import com.suwiki.data.datasource.remote.RemoteExamReportDataSource
import com.suwiki.data.datasource.remote.RemoteLectureReportDataSource
import com.suwiki.lectureevaluation.report.datasource.RemoteExamReportDataSourceImpl
import com.suwiki.lectureevaluation.report.datasource.RemoteLectureReportDataSourceImpl
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
    abstract fun bindRemoteExamReportDatasource(
        remoteOpenMajorDataSourceImpl: RemoteExamReportDataSourceImpl,
    ): RemoteExamReportDataSource

    @Singleton
    @Binds
    abstract fun bindLectureExamReportDatasource(
        remoteOpenMajorDataSourceImpl: RemoteLectureReportDataSourceImpl,
    ): RemoteLectureReportDataSource
}
