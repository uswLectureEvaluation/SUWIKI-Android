package com.suwiki.remote.common.repository

import com.suwiki.common.security.SecurityPreferences
import com.suwiki.remote.common.api.AuthApi
import com.suwiki.remote.common.di.RETROFIT_TAG
import com.suwiki.remote.common.retrofit.onFailure
import com.suwiki.remote.common.retrofit.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthRepositoryImpl @Inject constructor(
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
    authApi
      .reissueRefreshToken(refreshToken.first())
      .onSuccess {
        saveTokens(access = it.accessToken, refresh = it.refreshToken)
      }
      .onFailure {
        securityPreferences.clearAll()
        Timber.tag(RETROFIT_TAG)
          .d("TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다. $it")
      }
      .isSuccess
}
