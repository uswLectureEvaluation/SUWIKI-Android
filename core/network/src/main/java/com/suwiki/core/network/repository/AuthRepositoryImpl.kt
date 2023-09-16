package com.suwiki.core.network.repository

import com.suwiki.core.network.api.AuthApi
import com.suwiki.core.network.retrofit.onFailure
import com.suwiki.core.network.retrofit.onSuccess
import com.suwiki.database.SecurityPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val securityPreferences: SecurityPreferences,
) : AuthRepository {
    private val refreshToken: Flow<String>
        get() = securityPreferences.flowRefreshToken()
    override val accessToken: Flow<String>
        get() = securityPreferences.flowAccessToken()

    private suspend fun saveTokens(access: String, refresh: String) {
        securityPreferences.setAccessToken(access)
        securityPreferences.setRefreshToken(refresh)
    }

    override suspend fun reissueRefreshToken(): Boolean =
        authApi.reissueRefreshToken(refreshToken.first()).onSuccess {
            saveTokens(access = it.accessToken, refresh = it.refreshToken)
        }.onFailure {
            securityPreferences.clearAll()
            Timber.tag("Network")
                .d("TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.")
        }.isSuccess
}
