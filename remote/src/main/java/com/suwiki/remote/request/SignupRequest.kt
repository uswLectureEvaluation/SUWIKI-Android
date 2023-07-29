package com.suwiki.remote.request

data class SignupRequest(
    val id: String,
    val password: String,
    val email: String,
)