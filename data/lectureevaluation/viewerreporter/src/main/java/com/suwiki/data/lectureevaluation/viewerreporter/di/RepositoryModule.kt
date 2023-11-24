package com.suwiki.data.lectureevaluation.viewerreporter.di

import com.suwiki.data.lectureevaluation.viewerreporter.repository.ExamProviderRepositoryImpl
import com.suwiki.data.lectureevaluation.viewerreporter.repository.ExamReportRepositoryImpl
import com.suwiki.data.lectureevaluation.viewerreporter.repository.LectureProviderRepositoryImpl
import com.suwiki.data.lectureevaluation.viewerreporter.repository.LectureReportRepositoryImpl
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamProviderRepository
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamReportRepository
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureProviderRepository
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureReportRepository
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
