package com.suwiki.remote.user.request

data class FindPasswordRequest(
  val loginId: String,
  val email: String,
)
