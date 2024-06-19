package com.suwiki.remote.common.di

import com.suwiki.remote.common.api.AuthApi
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
  fun provideAuthApi(@NoAuthRetrofit retrofit: Retrofit): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }
}
