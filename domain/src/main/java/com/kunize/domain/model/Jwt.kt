package com.kunize.domain.model

data class Jwt(
    val accessToken: String,
    val refreshToken: String
)
