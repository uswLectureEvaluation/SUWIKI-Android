package com.suwiki.remote.signup.di

import com.suwiki.remote.common.di.NoAuthRetrofit
import com.suwiki.remote.signup.api.SignUpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Provides
  fun provideSignUpApi(@NoAuthRetrofit retrofit: Retrofit): SignUpApi {
    return retrofit.create(SignUpApi::class.java)
  }
}
