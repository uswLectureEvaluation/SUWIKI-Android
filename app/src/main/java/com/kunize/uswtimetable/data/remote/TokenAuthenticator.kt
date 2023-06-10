package com.kunize.uswtimetable.data.remote

import android.util.Log
import com.kunize.uswtimetable.domain.repository.AuthRepository
import com.kunize.uswtimetable.domain.repository.LogoutRepository
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.SuwikiApplication
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
        val refresh = SuwikiApplication.encryptedPrefs.getRefreshToken() ?: ""
        Log.d(
            Constants.TAG,
            "TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh",
        )

        return if (authRepository.requestRefreshToken(refresh)) {
            Log.d(Constants.TAG, "TokenAuthenticator - authenticate() called / 중단된 API 재요청")
            response.request
                .newBuilder()
                .removeHeader(AUTH_HEADER)
                .header(AUTH_HEADER, SuwikiApplication.encryptedPrefs.getAccessToken() ?: "")
                .build()
        } else {
            handleFailure()
            null
        }
    }

    private fun handleFailure() {
        runBlocking { logoutRepository.logout() }
        Log.d(
            Constants.TAG,
            "TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.",
        )
    }

    companion object {
        const val AUTH_HEADER = "Authorization"
    }
}
