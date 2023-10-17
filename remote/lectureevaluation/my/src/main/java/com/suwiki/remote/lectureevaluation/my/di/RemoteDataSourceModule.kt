package com.suwiki.remote.lectureevaluation.my.di

import com.suwiki.data.datasource.remote.RemoteExamMyDataSource
import com.suwiki.data.datasource.remote.RemoteLectureMyDataSource
import com.suwiki.remote.lectureevaluation.my.datasource.RemoteExamMyDataSourceImpl
import com.suwiki.remote.lectureevaluation.my.datasource.RemoteLectureMyDataSourceImpl
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
    abstract fun bindRemoteExamMyDatasource(
        remoteOpenMajorDataSourceImpl: RemoteExamMyDataSourceImpl,
    ): RemoteExamMyDataSource

    @Singleton
    @Binds
    abstract fun bindLectureExamMyDatasource(
        remoteOpenMajorDataSourceImpl: RemoteLectureMyDataSourceImpl,
    ): RemoteLectureMyDataSource
}
