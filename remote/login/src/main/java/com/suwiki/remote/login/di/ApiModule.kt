package com.suwiki.remote.login.di

import com.suwiki.remote.common.di.AuthRetrofit
import com.suwiki.remote.login.api.LoginApi
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
  fun provideUserApi(@AuthRetrofit retrofit: Retrofit): LoginApi {
    return retrofit.create(LoginApi::class.java)
  }
}
