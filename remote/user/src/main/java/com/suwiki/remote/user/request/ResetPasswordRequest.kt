package com.suwiki.remote.user.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
  val currentPassword: String,
  val newPassword: String,
)
