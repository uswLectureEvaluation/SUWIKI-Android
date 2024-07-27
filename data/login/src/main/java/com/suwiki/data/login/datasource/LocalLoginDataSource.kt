package com.suwiki.data.login.datasource

import com.suwiki.common.model.user.Token

interface LocalLoginDataSource {
  suspend fun setToken(
    token: Token,
  )
}
