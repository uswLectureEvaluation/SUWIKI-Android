package com.suwiki.signup.di

import com.suwiki.core.network.di.NormalRetrofit
import com.suwiki.signup.api.SignUpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideSignUpApi(@NormalRetrofit retrofit: Retrofit): SignUpApi {
        return retrofit.create(SignUpApi::class.java)
    }
}