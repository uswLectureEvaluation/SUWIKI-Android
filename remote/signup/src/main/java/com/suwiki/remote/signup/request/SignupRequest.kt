package com.suwiki.remote.signup.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
  val loginId: String,
  val password: String,
  val email: String,
)
