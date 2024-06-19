package com.suwiki.remote.notice.di

import com.suwiki.remote.common.di.NoAuthRetrofit
import com.suwiki.remote.notice.api.NoticeApi
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
  fun provideNoticeApi(@NoAuthRetrofit retrofit: Retrofit): NoticeApi {
    return retrofit.create(NoticeApi::class.java)
  }
}
