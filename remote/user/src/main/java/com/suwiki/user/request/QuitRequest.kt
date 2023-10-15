package com.suwiki.user.request

data class QuitRequest(
    val id: String,
    val password: String,
)