package com.suwiki.remote.user.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
  val loginId: String,
  val password: String,
)
