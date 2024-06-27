package com.suwiki.remote.lectureevaluation.di

import com.suwiki.remote.common.di.AuthRetrofit
import com.suwiki.remote.lectureevaluation.api.ExamEditorApi
import com.suwiki.remote.lectureevaluation.api.ExamMyApi
import com.suwiki.remote.lectureevaluation.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.api.LectureEditorApi
import com.suwiki.remote.lectureevaluation.api.LectureMyApi
import com.suwiki.remote.lectureevaluation.api.LectureReportApi
import com.suwiki.remote.lectureevaluation.api.LectureViewerApi
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
  fun provideExamEditorApi(@AuthRetrofit retrofit: Retrofit): ExamEditorApi {
    return retrofit.create(ExamEditorApi::class.java)
  }

  @Singleton
  @Provides
  fun provideLectureEditorApi(@AuthRetrofit retrofit: Retrofit): LectureEditorApi {
    return retrofit.create(LectureEditorApi::class.java)
  }

  @Singleton
  @Provides
  fun provideExamMyApi(@AuthRetrofit retrofit: Retrofit): ExamMyApi {
    return retrofit.create(ExamMyApi::class.java)
  }

  @Singleton
  @Provides
  fun provideLectureMyApi(@AuthRetrofit retrofit: Retrofit): LectureMyApi {
    return retrofit.create(LectureMyApi::class.java)
  }

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
