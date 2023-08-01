package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalAuthDataSource
import com.suwiki.local.datastore.SecurityPreferences
import com.suwiki.model.Token
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : LocalAuthDataSource {
    override fun getAccessToken(): Flow<String> = securityPreferences.flowAccessToken()

    override fun getRefreshToken(): Flow<String> = securityPreferences.flowRefreshToken()

    override suspend fun saveAccessToken(accessToken: String) {
        securityPreferences.setAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        securityPreferences.setRefreshToken(refreshToken)
    }

    override suspend fun saveToken(token: Token) {
        saveAccessToken(token.accessToken)
        saveRefreshToken(token.refreshToken)
    }

    override suspend fun clearToken() {
        saveToken(Token("", ""))
    }
}
