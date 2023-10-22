package com.suwiki.remote.lectureevaluation.viewer.di

import com.suwiki.core.network.di.AuthRetrofit
import com.suwiki.remote.lectureevaluation.viewer.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.viewer.api.LectureViewerApi
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
  fun provideExamViewerApi(@AuthRetrofit retrofit: Retrofit): ExamViewerApi {
    return retrofit.create(ExamViewerApi::class.java)
  }

  @Singleton
  @Provides
  fun provideLectureViewerApi(@AuthRetrofit retrofit: Retrofit): LectureViewerApi {
    return retrofit.create(LectureViewerApi::class.java)
  }
}
