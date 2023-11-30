package com.suwiki.domain.user.repository

import com.suwiki.core.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

  suspend fun logout()

  suspend fun findId(email: String)

  suspend fun findPassword(
    loginId: String,
    email: String,
  )

  suspend fun resetPassword(
    currentPassword: String,
    newPassword: String,
  )

  suspend fun login(
    loginId: String,
    password: String,
  )

  suspend fun quit(
    id: String,
    password: String,
  )

  suspend fun getUserInfo(): Flow<User>
}
