package com.suwiki.data.user.repository

import com.suwiki.core.model.user.User
import com.suwiki.data.user.datasource.LocalUserProviderDataSource
import com.suwiki.data.user.datasource.LocalUserStorageDataSource
import com.suwiki.data.user.datasource.RemoteUserDataSource
import com.suwiki.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
  private val localUserProviderDataSource: LocalUserProviderDataSource,
  private val localUserStorageDataSource: LocalUserStorageDataSource,
  private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {
  override suspend fun logout() {
    localUserStorageDataSource.clearUserInfoAndToken()
  }

  override suspend fun findId(email: String) {
    remoteUserDataSource.findId(email = email)
  }

  override suspend fun findPassword(loginId: String, email: String) {
    remoteUserDataSource.findPassword(
      loginId = loginId,
      email = email,
    )
  }

  override suspend fun resetPassword(currentPassword: String, newPassword: String) {
    remoteUserDataSource.resetPassword(
      currentPassword = currentPassword,
      newPassword = newPassword,
    )
  }

  override suspend fun login(loginId: String, password: String) {
    val token = remoteUserDataSource.login(
      loginId = loginId,
      password = password,
    )

    localUserStorageDataSource.setToken(token)
  }

  override suspend fun quit(id: String, password: String) {
    remoteUserDataSource.quit(
      id = id,
      password = password,
    )
  }

  override suspend fun getUserInfo(): Flow<User> = flow {
    val localUserInfo = localUserProviderDataSource.user.first()
    emit(localUserInfo)

    val remoteUserInfo = remoteUserDataSource.getUserInfo()
    emit(remoteUserInfo)

    localUserStorageDataSource.setUserInfo(remoteUserInfo)
  }
}
