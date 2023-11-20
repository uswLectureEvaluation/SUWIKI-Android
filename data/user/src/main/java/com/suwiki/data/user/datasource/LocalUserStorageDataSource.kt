package com.suwiki.data.user.datasource

import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User

interface LocalUserStorageDataSource {
  suspend fun login(
    user: User,
    token: Token,
  )

  suspend fun logout()
}
