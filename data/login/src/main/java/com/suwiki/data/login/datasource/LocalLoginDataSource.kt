package com.suwiki.data.login.datasource

import com.suwiki.core.model.user.Token

interface LocalLoginDataSource {
  suspend fun setToken(
    token: Token
  )
}
