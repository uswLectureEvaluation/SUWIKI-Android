package com.suwiki.di.network

import com.suwiki.di.AuthRetrofit
import com.suwiki.remote.api.EvaluateApi
import com.suwiki.remote.api.ExamApi
import com.suwiki.remote.api.LectureApi
import com.suwiki.remote.api.MajorApi
import com.suwiki.remote.api.NoticeApi
import com.suwiki.remote.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideEvaluateApi(@AuthRetrofit retrofit: Retrofit): EvaluateApi {
        return retrofit.create(EvaluateApi::class.java)
    }

    @Provides
    fun provideExamApi(@AuthRetrofit retrofit: Retrofit): ExamApi {
        return retrofit.create(ExamApi::class.java)
    }

    @Provides
    fun provideLectureApi(@AuthRetrofit retrofit: Retrofit): LectureApi {
        return retrofit.create(LectureApi::class.java)
    }

    @Provides
    fun provideMajorApi(@AuthRetrofit retrofit: Retrofit): MajorApi {
        return retrofit.create(MajorApi::class.java)
    }

    @Provides
    fun provideNoticeApi(@AuthRetrofit retrofit: Retrofit): NoticeApi {
        return retrofit.create(NoticeApi::class.java)
    }

    @Provides
    fun provideUserApi(@AuthRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}
