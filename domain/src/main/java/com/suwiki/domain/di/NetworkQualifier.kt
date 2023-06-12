package com.suwiki.domain.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatorInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor
