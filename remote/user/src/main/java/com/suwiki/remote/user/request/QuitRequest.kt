package com.suwiki.remote.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuitRequest(
  @SerialName("loginId")
  val id: String,
  val password: String,
)
