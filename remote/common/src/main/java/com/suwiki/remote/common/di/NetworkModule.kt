package com.suwiki.remote.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.suwiki.remote.common.BuildConfig
import com.suwiki.remote.common.authenticator.TokenAuthenticator
import com.suwiki.remote.common.interceptor.AuthenticationInterceptor
import com.suwiki.remote.common.retrofit.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  @NoAuthOkHttpClient
  fun provideNoAuthHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .readTimeout(10, TimeUnit.SECONDS)
      .connectTimeout(10, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .build()
  }

  @Singleton
  @Provides
  fun provideJson(): Json {
    return Json {
      prettyPrint = true
      coerceInputValues = true
      ignoreUnknownKeys = true
    }
  }

  @Singleton
  @Provides
  fun provideLoggingInterceptor(
    json: Json,
  ): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor { message ->
      when {
        !message.isJsonObject() && !message.isJsonArray() ->
          Timber.tag(RETROFIT_TAG).d("CONNECTION INFO -> $message")

        else -> kotlin.runCatching {
          json.encodeToString(Json.parseToJsonElement(message))
        }.onSuccess {
          Timber.tag(RETROFIT_TAG).d(it)
        }.onFailure {
          Timber.tag(RETROFIT_TAG).d(message)
        }
      }
    }
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
  }

  @Singleton
  @Provides
  @NoAuthRetrofit
  fun provideNoAuthRetrofit(
    @NoAuthOkHttpClient okHttpClient: OkHttpClient,
    json: Json,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .client(okHttpClient)
      .addCallAdapterFactory(ResultCallAdapterFactory())
      .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
      .build()
  }

  @Singleton
  @Provides
  @AuthOkHttpClient
  internal fun provideAuthHttpClient(
    authenticator: TokenAuthenticator,
    authenticationInterceptor: AuthenticationInterceptor,
    loggingInterceptor: HttpLoggingInterceptor,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .readTimeout(10, TimeUnit.SECONDS)
      .connectTimeout(10, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .addInterceptor(authenticationInterceptor)
      .authenticator(authenticator)
      .build()
  }

  @Singleton
  @Provides
  @AuthRetrofit
  fun provideAuthRetrofit(
    @AuthOkHttpClient okHttpClient: OkHttpClient,
    json: Json,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .client(okHttpClient)
      .addCallAdapterFactory(ResultCallAdapterFactory())
      .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
      .build()
  }
}

private fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
private fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
