package com.suwiki.domain.login.repository

interface LoginRepository {

  suspend fun findId(email: String)

  suspend fun findPassword(
    loginId: String,
    email: String,
  )

  suspend fun login(
    loginId: String,
    password: String,
  )
}
