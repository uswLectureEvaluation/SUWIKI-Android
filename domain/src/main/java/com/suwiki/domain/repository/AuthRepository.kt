package com.suwiki.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
  val refreshToken: Flow<String>
  val accessToken: Flow<String>

  suspend fun saveTokens(refresh: String, access: String)
  suspend fun clearTokens()

  /**
   * 토큰 재발급
   * */
  suspend fun requestRefreshToken(): Boolean
}
