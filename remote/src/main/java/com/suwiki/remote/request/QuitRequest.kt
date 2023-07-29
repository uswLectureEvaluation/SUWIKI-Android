package com.suwiki.remote.request

data class QuitRequest(
    val id: String,
    val password: String,
)