package com.suwiki.remote.user.request

data class QuitRequest(
  val id: String,
  val password: String,
)
