package com.suwiki.openmajor.di

import com.suwiki.core.network.di.AuthRetrofit
import com.suwiki.openmajor.api.MajorApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideMajorApi(@AuthRetrofit retrofit: Retrofit): MajorApi {
        return retrofit.create(MajorApi::class.java)
    }
}