package com.suwiki.data.user.datasource

import com.suwiki.common.model.user.Suspension
import com.suwiki.common.model.user.User

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

  suspend fun getBanHistory(): List<Suspension.Ban>

  suspend fun getBlackList(): List<Suspension.Block>
}
