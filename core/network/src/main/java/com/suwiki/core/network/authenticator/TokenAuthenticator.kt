package com.suwiki.core.network.authenticator

import com.suwiki.core.network.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        Timber.tag("Network").d("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청")
        return runBlocking {
            return@runBlocking if (authRepository.reissueRefreshToken()) {
                Timber.tag("Network").d("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
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
