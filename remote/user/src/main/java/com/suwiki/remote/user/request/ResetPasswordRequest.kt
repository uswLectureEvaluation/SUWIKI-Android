package com.suwiki.remote.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
  @SerialName("prePassword")
  val currentPassword: String,
  val newPassword: String,
)
