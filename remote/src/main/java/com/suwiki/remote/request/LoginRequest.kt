package com.suwiki.remote.request

data class LoginRequest(
    val loginId: String,
    val password: String,
)