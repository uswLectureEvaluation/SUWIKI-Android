package com.suwiki.remote.request

data class ResetPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)