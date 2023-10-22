package com.suwiki.remote.user.request

data class LoginRequest(
  val loginId: String,
  val password: String,
)
