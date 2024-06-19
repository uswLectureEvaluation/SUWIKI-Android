package com.suwiki.remote.lectureevaluation.viewerreporter.di

import com.suwiki.remote.common.di.AuthRetrofit
import com.suwiki.remote.lectureevaluation.viewerreporter.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.viewerreporter.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.viewerreporter.api.LectureReportApi
import com.suwiki.remote.lectureevaluation.viewerreporter.api.LectureViewerApi
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

  @Singleton
  @Provides
  fun provideExamViewerApi(@AuthRetrofit retrofit: Retrofit): ExamViewerApi {
    return retrofit.create(ExamViewerApi::class.java)
  }

  @Singleton
  @Provides
  fun provideLectureViewerApi(@AuthRetrofit retrofit: Retrofit): LectureViewerApi {
    return retrofit.create(LectureViewerApi::class.java)
  }
}
