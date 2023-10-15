package com.suwiki.user.request

data class ResetPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)