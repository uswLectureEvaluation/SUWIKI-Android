package com.suwiki.remote.request.user

data class QuitRequest(
    val id: String,
    val password: String,
)