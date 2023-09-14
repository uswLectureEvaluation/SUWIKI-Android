package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalTokenStorageDataSource
import com.suwiki.local.datastore.SecurityPreferences
import com.suwiki.model.Token
import javax.inject.Inject

class LocalTokenStorageDataSourceImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : LocalTokenStorageDataSource {

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
