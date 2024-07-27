package com.suwiki.data.login.datasource

import com.suwiki.common.model.user.Token

interface RemoteLoginDataSource {
  suspend fun findId(email: String)

  suspend fun findPassword(
    loginId: String,
    email: String,
  )

  suspend fun login(
    loginId: String,
    password: String,
  ): Token
}
