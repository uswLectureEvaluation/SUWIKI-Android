package com.suwiki.remote.request.user

data class FindPasswordRequest(
    val loginId: String,
    val email: String,
)