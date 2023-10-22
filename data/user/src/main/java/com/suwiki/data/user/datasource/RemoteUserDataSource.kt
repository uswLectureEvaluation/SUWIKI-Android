package com.suwiki.data.user.datasource

import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User

interface RemoteUserDataSource {
  suspend fun findId(email: String): Boolean

  suspend fun findPassword(
    loginId: String,
    email: String,
  ): Boolean

  suspend fun resetPassword(
    currentPassword: String,
    newPassword: String,
  ): Boolean

  suspend fun login(
    loginId: String,
    password: String,
  ): Token

  suspend fun quit(
    id: String,
    password: String,
  ): Boolean

  suspend fun getUserData(): User
}
