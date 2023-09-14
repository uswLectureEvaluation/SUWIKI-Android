package com.suwiki.di.network

import com.suwiki.data.datasource.remote.RemoteExamMyDataSource
import com.suwiki.data.datasource.remote.RemoteExamProviderDataSource
import com.suwiki.data.datasource.remote.RemoteExamReportDataSource
import com.suwiki.data.datasource.remote.RemoteExamStorageDataSource
import com.suwiki.data.datasource.remote.RemoteLectureMyDataSource
import com.suwiki.data.datasource.remote.RemoteLectureProviderDataSource
import com.suwiki.data.datasource.remote.RemoteLectureReportDataSource
import com.suwiki.data.datasource.remote.RemoteLectureStorageDataSource
import com.suwiki.data.datasource.remote.RemoteNoticeDataSource
import com.suwiki.data.datasource.remote.RemoteOpenMajorDataSource
import com.suwiki.data.datasource.remote.RemoteTimetableDataSource
import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.remote.datasource.RemoteExamMyDataSourceImpl
import com.suwiki.remote.datasource.RemoteExamProviderDataSourceImpl
import com.suwiki.remote.datasource.RemoteExamReportDataSourceImpl
import com.suwiki.remote.datasource.RemoteExamStorageDataSourceImpl
import com.suwiki.remote.datasource.RemoteLectureMyDataSourceImpl
import com.suwiki.remote.datasource.RemoteLectureProviderDataSourceImpl
import com.suwiki.remote.datasource.RemoteLectureReportDataSourceImpl
import com.suwiki.remote.datasource.RemoteLectureStorageDataSourceImpl
import com.suwiki.remote.datasource.RemoteNoticeDataSourceImpl
import com.suwiki.remote.datasource.RemoteOpenMajorDataSourceImpl
import com.suwiki.remote.datasource.RemoteTimetableDataSourceImpl
import com.suwiki.remote.datasource.RemoteUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteLectureMyDataSource(
        remoteLectureMyDataSourceImpl: RemoteLectureMyDataSourceImpl,
    ): RemoteLectureMyDataSource

    @Binds
    abstract fun bindRemoteLectureProviderDataSource(
        remoteLectureProviderDataSourceImpl: RemoteLectureProviderDataSourceImpl,
    ): RemoteLectureProviderDataSource

    @Binds
    abstract fun bindRemoteLectureStorageDataSource(
        remoteLectureStorageDataSourceImpl: RemoteLectureStorageDataSourceImpl,
    ): RemoteLectureStorageDataSource

    @Binds
    abstract fun bindRemoteLectureReportDataSource(
        remoteLectureReportDataSourceImpl: RemoteLectureReportDataSourceImpl,
    ): RemoteLectureReportDataSource

    @Binds
    abstract fun bindRemoteExamMyDataSource(
        remoteExamMyDataSourceImpl: RemoteExamMyDataSourceImpl,
    ): RemoteExamMyDataSource

    @Binds
    abstract fun bindRemoteExamProviderDataSource(
        remoteExamProviderDataSourceImpl: RemoteExamProviderDataSourceImpl,
    ): RemoteExamProviderDataSource

    @Binds
    abstract fun bindRemoteExamReportDataSource(
        remoteExamReportDataSourceImpl: RemoteExamReportDataSourceImpl,
    ): RemoteExamReportDataSource

    @Binds
    abstract fun bindRemoteExamStorageDataSource(
        remoteExamStorageDataSourceImpl: RemoteExamStorageDataSourceImpl,
    ): RemoteExamStorageDataSource

    @Binds
    abstract fun bindRemoteNoticeDatasource(
        remoteNoticeDataSourceImpl: RemoteNoticeDataSourceImpl,
    ): RemoteNoticeDataSource

    @Binds
    abstract fun bindRemoteOpenMajorDatasource(
        remoteOpenMajorDataSourceImpl: RemoteOpenMajorDataSourceImpl,
    ): RemoteOpenMajorDataSource

    @Binds
    abstract fun bindRemoteTimetableDatasource(
        remoteTimetableDataSourceImpl: RemoteTimetableDataSourceImpl,
    ): RemoteTimetableDataSource

    @Binds
    abstract fun bindRemoteUserDataSource(
        remoteUserDataSourceImpl: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource
}
