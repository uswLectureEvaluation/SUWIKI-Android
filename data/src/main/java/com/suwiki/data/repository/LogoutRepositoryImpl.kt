package com.suwiki.data.repository

import com.suwiki.data.model.UserPreference
import com.suwiki.domain.repository.AuthRepository
import com.suwiki.domain.repository.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
  private val userPreference: UserPreference,
  private val authRepository: AuthRepository,
) : LogoutRepository {

  override suspend fun logout() {
    userPreference.logout()
    authRepository.clearTokens()
  }
}
