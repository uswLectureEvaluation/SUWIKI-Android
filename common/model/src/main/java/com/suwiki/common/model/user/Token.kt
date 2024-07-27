package com.suwiki.common.model.user

data class Token(
  val accessToken: String,
  val refreshToken: String,
)
