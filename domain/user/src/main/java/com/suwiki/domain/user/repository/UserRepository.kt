package com.suwiki.domain.user.repository

import com.suwiki.core.model.user.Suspension
import com.suwiki.core.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

  suspend fun logout()

  suspend fun resetPassword(
    currentPassword: String,
    newPassword: String,
  )

  suspend fun quit(
    id: String,
    password: String,
  )

  suspend fun getUserInfo(): Flow<User>

  suspend fun getBanHistory(): List<Suspension.Ban>
}
