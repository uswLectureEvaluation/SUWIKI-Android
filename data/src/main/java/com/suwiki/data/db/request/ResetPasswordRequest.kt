package com.suwiki.data.db.request

data class ResetPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)
