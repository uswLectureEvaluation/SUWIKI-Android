package com.suwiki.local.token.storage.datasource

import com.suwiki.data.datasource.local.LocalTokenStorageDataSource
import com.suwiki.core.model.Token
import com.suwiki.core.security.SecurityPreferences
import javax.inject.Inject

class LocalTokenStorageDataSourceImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : LocalTokenStorageDataSource {

    override suspend fun saveToken(token: Token) {
        securityPreferences.setAccessToken(token.accessToken)
        securityPreferences.setRefreshToken(token.refreshToken)
    }

    override suspend fun clearToken() {
        saveToken(Token("", ""))
    }
}