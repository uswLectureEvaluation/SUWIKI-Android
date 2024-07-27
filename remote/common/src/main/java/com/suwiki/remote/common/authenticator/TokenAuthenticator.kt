package com.suwiki.remote.common.authenticator

import com.suwiki.common.model.exception.SuwikiServerError
import com.suwiki.remote.common.di.AUTH_HEADER
import com.suwiki.remote.common.di.RETROFIT_TAG
import com.suwiki.remote.common.repository.AuthRepository
import com.suwiki.remote.common.retrofit.Json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

internal class TokenAuthenticator @Inject constructor(
  private val authRepository: AuthRepository,
) : Authenticator {
  private val mutex = Mutex()
  override fun authenticate(route: Route?, response: okhttp3.Response): Request? = runBlocking {
    if (response.isTokenExpired.not()) return@runBlocking null

    mutex.withLock {
      val accessToken = authRepository.accessToken.first()
      val alreadyRefreshed =
        response.request.header(AUTH_HEADER)?.contains(accessToken) == false
      // if request's header's token is different, then that means the access token has already been refreshed
      // we return the response with the locally persisted token in the header
      if (alreadyRefreshed) {
        Timber.tag(RETROFIT_TAG)
          .d("TokenAuthenticator - 이미 토큰이 갱신됨 / 중단된 API 재요청")

        return@runBlocking response.request.newBuilder()
          .header(AUTH_HEADER, accessToken)
          .build()
      }

      if (authRepository.reissueRefreshToken()) {
        Timber.tag(RETROFIT_TAG)
          .d("TokenAuthenticator - 토큰 갱신 성공 / 중단된 API 재요청")
        response.request
          .newBuilder()
          .removeHeader(AUTH_HEADER)
          .header(AUTH_HEADER, authRepository.accessToken.first())
          .build()
      } else {
        null
      }
    }
  }
}

private val okhttp3.Response.isTokenExpired: Boolean
  get() {
    val exception = kotlin.runCatching {
      // https://stackoverflow.com/questions/60671465/retrofit-java-lang-illegalstateexception-closed
      val suwikiErrorResponse = Json.getSuwikiErrorBody(peekBody(Long.MAX_VALUE).string())
      SuwikiServerError.valueOf(suwikiErrorResponse.suwikiCode).exception
    }.getOrNull() ?: return false

    return exception == SuwikiServerError.TOKEN001.exception && code == 401
  }
