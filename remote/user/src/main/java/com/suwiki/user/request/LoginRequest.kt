package com.suwiki.user.request

data class LoginRequest(
    val loginId: String,
    val password: String,
)