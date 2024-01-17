package com.suwiki.data.user.repository

import com.suwiki.core.model.exception.AuthorizationException
import com.suwiki.core.model.user.Suspension
import com.suwiki.core.model.user.User
import com.suwiki.core.security.SecurityPreferences
import com.suwiki.data.user.datasource.LocalUserDataSource
import com.suwiki.data.user.datasource.RemoteUserDataSource
import com.suwiki.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
  private val localUserDataSource: LocalUserDataSource,
  private val remoteUserDataSource: RemoteUserDataSource,
  private val securityPreferences: SecurityPreferences,
) : UserRepository {
  override suspend fun logout() {
    localUserDataSource.clearUserInfo()
    securityPreferences.clearAll()
  }

  override suspend fun resetPassword(currentPassword: String, newPassword: String) {
    remoteUserDataSource.resetPassword(
      currentPassword = currentPassword,
      newPassword = newPassword,
    )
  }

  override suspend fun quit(id: String, password: String) {
    remoteUserDataSource.quit(
      id = id,
      password = password,
    )
    logout()
  }

  override suspend fun getUserInfo(): Flow<User> = flow {
    val localUserInfo = localUserDataSource.user.first()
    emit(localUserInfo)

    val remoteUserInfo = runCatching {
      remoteUserDataSource.getUserInfo()
    }.getOrElse { exception ->
      if (exception is AuthorizationException) {
        logout()
        emit(User())
        return@flow
      } else {
        throw exception
      }
    }

    emit(remoteUserInfo)
    localUserDataSource.setUserInfo(remoteUserInfo)
  }

  override suspend fun getBanHistory(): List<Suspension.Ban> {
    return remoteUserDataSource.getBanHistory()
  }

  override suspend fun getBlackList(): List<Suspension.Block> {
    return remoteUserDataSource.getBlackList()
  }
}
