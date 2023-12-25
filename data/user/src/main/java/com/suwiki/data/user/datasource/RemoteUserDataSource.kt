package com.suwiki.data.user.datasource

import com.suwiki.core.model.user.User

interface RemoteUserDataSource {

  suspend fun resetPassword(
    currentPassword: String,
    newPassword: String,
  )

  suspend fun quit(
    id: String,
    password: String,
  )

  suspend fun getUserInfo(): User
}
