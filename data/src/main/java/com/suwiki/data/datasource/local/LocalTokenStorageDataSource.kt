package com.suwiki.data.datasource.local

import com.suwiki.model.Token

interface LocalTokenStorageDataSource {

    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun saveToken(token: Token)

    suspend fun clearToken()
}
