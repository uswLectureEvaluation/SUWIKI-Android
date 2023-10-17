package com.suwiki.remote.signup.request

data class SignupRequest(
    val id: String,
    val password: String,
    val email: String,
)