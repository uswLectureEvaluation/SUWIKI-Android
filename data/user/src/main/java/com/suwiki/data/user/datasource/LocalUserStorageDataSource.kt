package com.suwiki.data.user.datasource

import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User

interface LocalUserStorageDataSource {
  suspend fun setUserInfo(
    user: User,
  )

  suspend fun setToken(
    token: Token,
  )

  suspend fun clearUserInfoAndToken()
}
