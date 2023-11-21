package com.suwiki.data.user.datasource

import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User

interface LocalUserStorageDataSource {
  suspend fun setUserInfoAndToken(
    user: User,
    token: Token,
  )

  suspend fun clearUserInfoAndToken()
}
