package com.suwiki.remote.request

data class FindPasswordRequest(
    val loginId: String,
    val email: String,
)