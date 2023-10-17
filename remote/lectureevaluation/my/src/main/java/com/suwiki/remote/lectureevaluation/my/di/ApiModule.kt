package com.suwiki.remote.lectureevaluation.my.di

import com.suwiki.core.network.di.AuthRetrofit
import com.suwiki.remote.lectureevaluation.my.api.ExamMyApi
import com.suwiki.remote.lectureevaluation.my.api.LectureMyApi
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
    fun provideExamMyApi(@AuthRetrofit retrofit: Retrofit): ExamMyApi {
        return retrofit.create(ExamMyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLectureMyApi(@AuthRetrofit retrofit: Retrofit): LectureMyApi {
        return retrofit.create(LectureMyApi::class.java)
    }
}
