package com.suwiki.remote.lectureevaluation.report.di

import com.suwiki.core.network.di.AuthRetrofit
import com.suwiki.remote.lectureevaluation.report.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.report.api.LectureReportApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Singleton
  @Provides
  fun provideExamReportApi(@AuthRetrofit retrofit: Retrofit): ExamReportApi {
    return retrofit.create(ExamReportApi::class.java)
  }

  @Singleton
  @Provides
  fun provideLectureReportApi(@AuthRetrofit retrofit: Retrofit): LectureReportApi {
    return retrofit.create(LectureReportApi::class.java)
  }
}
