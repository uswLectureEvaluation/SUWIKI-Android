package com.suwiki.remote.user.request

import kotlinx.serialization.Serializable

@Serializable
data class FindPasswordRequest(
  val loginId: String,
  val email: String,
)
