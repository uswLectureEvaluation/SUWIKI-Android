package com.suwiki.remote.common.interceptor

import com.suwiki.remote.common.di.AUTH_HEADER
import com.suwiki.remote.common.di.RETROFIT_TAG
import com.suwiki.remote.common.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import timber.log.Timber
import javax.inject.Inject

internal class AuthenticationInterceptor @Inject constructor(
  private val authRepository: AuthRepository,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
    return runBlocking {
      val accessToken = authRepository.accessToken.first()
      val request = chain.request().newBuilder()
        .addHeader(AUTH_HEADER, accessToken).build()
      Timber.tag(RETROFIT_TAG)
        .d(
          "AuthenticationInterceptor - intercept() called / request header: %s",
          accessToken,
        )
      return@runBlocking chain.proceed(request)
    }
  }
}
