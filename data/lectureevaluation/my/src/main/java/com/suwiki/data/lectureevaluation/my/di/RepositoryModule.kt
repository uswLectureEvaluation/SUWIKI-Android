package com.suwiki.data.lectureevaluation.my.di

import com.suwiki.data.lectureevaluation.my.repository.ExamMyRepositoryImpl
import com.suwiki.data.lectureevaluation.my.repository.LectureMyRepositoryImpl
import com.suwiki.domain.lectureevaluation.my.repository.ExamMyRepository
import com.suwiki.domain.lectureevaluation.my.repository.LectureMyRepository
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
  abstract fun bindExamMyRepository(
    examMyRepositoryImpl: ExamMyRepositoryImpl,
  ): ExamMyRepository

  @Singleton
  @Binds
  abstract fun bindLectureMyRepository(
    lectureMyRepositoryImpl: LectureMyRepositoryImpl,
  ): LectureMyRepository
}
