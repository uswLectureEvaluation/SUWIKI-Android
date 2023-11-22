package com.suwiki.remote.user.request

import kotlinx.serialization.Serializable

@Serializable
data class QuitRequest(
  val id: String,
  val password: String,
)
