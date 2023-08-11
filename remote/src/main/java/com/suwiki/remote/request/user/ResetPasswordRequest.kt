package com.suwiki.remote.request.user

data class ResetPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)