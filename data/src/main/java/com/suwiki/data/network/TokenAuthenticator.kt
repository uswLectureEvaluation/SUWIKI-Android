package com.suwiki.data.network

import android.util.Log
import com.suwiki.domain.repository.AuthRepository
import com.suwiki.domain.repository.LogoutRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val logoutRepository: LogoutRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        /*val refresh = SuwikiApplication.encryptedPrefs.getRefreshToken() ?: "" */
        Log.d(
            "Network",
            "TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청",
        )
        return runBlocking {
            return@runBlocking if (authRepository.requestRefreshToken()) {
                Log.d("Network", "TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader(AUTH_HEADER)
                    .header(AUTH_HEADER, authRepository.accessToken.first())
                    .build()
            } else {
                handleFailure()
                null
            }
        }
    }

    private fun handleFailure() {
        runBlocking { logoutRepository.logout() }
        Log.d(
            "Network",
            "TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.",
        )
    }

    companion object {
        const val AUTH_HEADER = "Authorization"
    }
}
