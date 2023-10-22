package com.suwiki.remote.lectureevaluation.editor.di

import com.suwiki.data.lectureevaluation.editor.datasource.RemoteExamEditorDataSource
import com.suwiki.data.lectureevaluation.editor.datasource.RemoteLectureEditorDataSource
import com.suwiki.remote.lectureevaluation.editor.datasource.RemoteExamEditorDataSourceImpl
import com.suwiki.remote.lectureevaluation.editor.datasource.RemoteLectureEditorDataSourceImpl
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
}
