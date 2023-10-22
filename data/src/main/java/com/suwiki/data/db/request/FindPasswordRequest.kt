package com.suwiki.data.db.request

data class FindPasswordRequest(
  val loginId: String,
  val email: String,
)
