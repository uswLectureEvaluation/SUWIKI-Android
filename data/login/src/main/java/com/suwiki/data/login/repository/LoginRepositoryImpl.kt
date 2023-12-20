package com.suwiki.data.login.repository

import com.suwiki.data.login.datasource.LocalLoginDataSource
import com.suwiki.data.login.datasource.RemoteLoginDataSource
import com.suwiki.domain.login.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
  private val remoteLoginDataSource: RemoteLoginDataSource,
  private val localLoginDataSource: LocalLoginDataSource,
) : LoginRepository {

  override suspend fun findId(email: String) {
    remoteLoginDataSource.findId(email = email)
  }

  override suspend fun findPassword(loginId: String, email: String) {
    remoteLoginDataSource.findPassword(
      loginId = loginId,
      email = email,
    )
  }

  override suspend fun login(loginId: String, password: String) {
    val token = remoteLoginDataSource.login(
      loginId = loginId,
      password = password,
    )

    localLoginDataSource.setToken(token)
  }
}
