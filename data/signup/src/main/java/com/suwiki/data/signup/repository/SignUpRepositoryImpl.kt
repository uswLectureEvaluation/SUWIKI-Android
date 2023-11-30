package com.suwiki.data.signup.repository

import com.suwiki.data.signup.datasource.RemoteSignUpDataSource
import com.suwiki.domain.signup.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
  private val remoteSignUpDataSource: RemoteSignUpDataSource,
) : SignUpRepository {
  override suspend fun signUp(id: String, password: String, email: String) {
    remoteSignUpDataSource.signUp(id = id, password = password, email = email)
  }

  override suspend fun checkIdOverlap(loginId: String): Boolean {
    return remoteSignUpDataSource.checkIdOverlap(loginId = loginId)
  }

  override suspend fun checkEmailOverlap(email: String): Boolean {
    return remoteSignUpDataSource.checkEmailOverlap(email = email)
  }
}
