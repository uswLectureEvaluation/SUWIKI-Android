package com.suwiki.lectureevaluation.editor.di

import com.suwiki.core.network.di.AuthRetrofit
import com.suwiki.lectureevaluation.editor.api.ExamEditorApi
import com.suwiki.lectureevaluation.editor.api.LectureEditorApi
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
    fun provideExamEditorApi(@AuthRetrofit retrofit: Retrofit): ExamEditorApi {
        return retrofit.create(ExamEditorApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLectureEditorApi(@AuthRetrofit retrofit: Retrofit): LectureEditorApi {
        return retrofit.create(LectureEditorApi::class.java)
    }
}