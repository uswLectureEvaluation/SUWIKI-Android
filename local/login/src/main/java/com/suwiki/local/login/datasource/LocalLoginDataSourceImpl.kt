package com.suwiki.local.login.datasource

import com.suwiki.core.model.user.Token
import com.suwiki.core.security.SecurityPreferences
import com.suwiki.data.login.datasource.LocalLoginDataSource
import javax.inject.Inject

class LocalLoginDataSourceImpl @Inject constructor(
  private val securityPreferences: SecurityPreferences,
) : LocalLoginDataSource {

  override suspend fun setToken(token: Token) {
    token.run {
      securityPreferences.setAccessToken(accessToken)
      securityPreferences.setRefreshToken(refreshToken)
    }
  }
}
