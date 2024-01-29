package com.suwiki.core.network.authenticator

import com.suwiki.core.model.exception.SuwikiServerError
import com.suwiki.core.network.di.RETROFIT_TAG
import com.suwiki.core.network.repository.AuthRepository
import com.suwiki.core.network.retrofit.Json
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

    val accessToken = authRepository.accessToken.first()
    val alreadyRefreshed = response.request.header(AUTH_HEADER)?.contains(accessToken) == false
    // if request's header's token is different, then that means the access token has already been refreshed
    // we return the response with the locally persisted token in the header
    if (alreadyRefreshed) {
      return@runBlocking response.request.newBuilder()
        .header(AUTH_HEADER, accessToken)
        .build()
    }


    Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청")
    mutex.withLock {
      if (authRepository.reissueRefreshToken()) {
        Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
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

  companion object {
    const val AUTH_HEADER = "Authorization"
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
