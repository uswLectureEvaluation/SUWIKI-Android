package com.suwiki.remote.request.user

data class SignupRequest(
    val id: String,
    val password: String,
    val email: String,
)