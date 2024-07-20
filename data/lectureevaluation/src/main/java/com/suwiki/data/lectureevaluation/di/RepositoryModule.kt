package com.suwiki.data.lectureevaluation.di

import com.suwiki.data.lectureevaluation.repository.ExamEditorRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.ExamMyRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.ExamProviderRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.ExamReportRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.LectureEditorRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.LectureMyRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.LectureProviderRepositoryImpl
import com.suwiki.data.lectureevaluation.repository.LectureReportRepositoryImpl
import com.suwiki.domain.lectureevaluation.repository.ExamEditorRepository
import com.suwiki.domain.lectureevaluation.repository.ExamMyRepository
import com.suwiki.domain.lectureevaluation.repository.ExamProviderRepository
import com.suwiki.domain.lectureevaluation.repository.ExamReportRepository
import com.suwiki.domain.lectureevaluation.repository.LectureEditorRepository
import com.suwiki.domain.lectureevaluation.repository.LectureMyRepository
import com.suwiki.domain.lectureevaluation.repository.LectureProviderRepository
import com.suwiki.domain.lectureevaluation.repository.LectureReportRepository
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

  @Singleton
  @Binds
  abstract fun bindExamMyRepository(
    examMyRepositoryImpl: ExamMyRepositoryImpl,
  ): ExamMyRepository

  @Singleton
  @Binds
  abstract fun bindLectureMyRepository(
    lectureMyRepositoryImpl: LectureMyRepositoryImpl,
  ): LectureMyRepository

  @Singleton
  @Binds
  abstract fun bindExamProviderRepository(
    examProviderRepositoryImpl: ExamProviderRepositoryImpl,
  ): ExamProviderRepository

  @Singleton
  @Binds
  abstract fun bindExamReportRepository(
    examReportRepositoryImpl: ExamReportRepositoryImpl,
  ): ExamReportRepository

  @Singleton
  @Binds
  abstract fun bindLectureProviderRepository(
    lectureProviderRepositoryImpl: LectureProviderRepositoryImpl,
  ): LectureProviderRepository

  @Singleton
  @Binds
  abstract fun bindLectureReportRepository(
    lectureReportRepositoryImpl: LectureReportRepositoryImpl,
  ): LectureReportRepository
}
