package com.suwiki.data.lectureevaluation.editor.di

import com.suwiki.data.lectureevaluation.editor.repository.ExamEditorRepositoryImpl
import com.suwiki.data.lectureevaluation.editor.repository.LectureEditorRepositoryImpl
import com.suwiki.domain.lectureevaluation.editor.repository.ExamEditorRepository
import com.suwiki.domain.lectureevaluation.editor.repository.LectureEditorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Singleton
  @Binds
  abstract fun bindExamEditorRepository(
    examEditorRepositoryImpl: ExamEditorRepositoryImpl,
  ): ExamEditorRepository

  @Singleton
  @Binds
  abstract fun bindLectureEditorRepository(
    lectureEditorRepositoryImpl: LectureEditorRepositoryImpl,
  ): LectureEditorRepository
}
