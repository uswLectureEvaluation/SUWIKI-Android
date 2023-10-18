package com.suwiki.core.model.user

data class Token(
    val accessToken: String,
    val refreshToken: String,
)