package com.suwiki.remote.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
  @SerialName("AccessToken") val accessToken: String,
  @SerialName("RefreshToken") val refreshToken: String,
)
