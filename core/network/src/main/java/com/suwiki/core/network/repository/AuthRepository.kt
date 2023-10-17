package com.suwiki.core.network.repository

import kotlinx.coroutines.flow.Flow

internal interface AuthRepository {
    val accessToken: Flow<String>

    /**
     * 토큰 재발급
     * */
    suspend fun reissueRefreshToken(): Boolean
}
