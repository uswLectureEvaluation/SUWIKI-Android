package com.suwiki.data.timetable.di

import com.suwiki.data.timetable.repository.OpenLectureRepositoryImpl
import com.suwiki.data.timetable.repository.TimetableRepositoryImpl
import com.suwiki.domain.timetable.repository.OpenLectureRepository
import com.suwiki.domain.timetable.repository.TimetableRepository
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
  abstract fun bindOpenLectureRepository(
    openLectureRepositoryImpl: OpenLectureRepositoryImpl,
  ): OpenLectureRepository

  @Singleton
  @Binds
  abstract fun bindTimetableRepository(
    timetableRepositoryImpl: TimetableRepositoryImpl,
  ): TimetableRepository
}
