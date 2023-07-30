package com.suwiki.remote.request.user

data class LoginRequest(
    val loginId: String,
    val password: String,
)