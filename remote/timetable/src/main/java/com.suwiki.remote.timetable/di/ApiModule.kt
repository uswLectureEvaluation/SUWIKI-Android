package com.suwiki.remote.timetable.di

import com.suwiki.core.network.di.NormalRetrofit
import com.suwiki.remote.timetable.api.OpenLectureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Provides
  fun provideOpenLectureApi(@NormalRetrofit retrofit: Retrofit): OpenLectureApi {
    return retrofit.create(OpenLectureApi::class.java)
  }
}
