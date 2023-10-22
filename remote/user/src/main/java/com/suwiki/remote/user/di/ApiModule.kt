package com.suwiki.remote.user.di

import com.suwiki.core.network.di.AuthRetrofit
import com.suwiki.remote.user.api.UserApi
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
  fun provideUserApi(@AuthRetrofit retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
  }
}
