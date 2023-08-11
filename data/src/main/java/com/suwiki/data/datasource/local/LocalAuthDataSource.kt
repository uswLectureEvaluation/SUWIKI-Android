package com.suwiki.data.datasource.local

import com.suwiki.model.Token
import kotlinx.coroutines.flow.Flow

interface LocalAuthDataSource {

    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>

    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun saveToken(token: Token)

    suspend fun clearToken()
}
