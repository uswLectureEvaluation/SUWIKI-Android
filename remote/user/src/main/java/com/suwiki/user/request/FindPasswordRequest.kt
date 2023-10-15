package com.suwiki.user.request

data class FindPasswordRequest(
    val loginId: String,
    val email: String,
)