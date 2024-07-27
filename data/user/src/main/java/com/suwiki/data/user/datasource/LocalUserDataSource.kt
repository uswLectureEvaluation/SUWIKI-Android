package com.suwiki.data.user.datasource

import com.suwiki.common.model.user.User
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
  val user: Flow<User>

  suspend fun setUserInfo(
    user: User,
  )

  suspend fun clearUserInfo()
}
