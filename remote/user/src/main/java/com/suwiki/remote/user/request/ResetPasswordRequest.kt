package com.suwiki.remote.user.request

data class ResetPasswordRequest(
  val currentPassword: String,
  val newPassword: String,
)
