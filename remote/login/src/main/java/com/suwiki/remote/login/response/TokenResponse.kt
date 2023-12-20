package com.suwiki.remote.login.response

import com.suwiki.core.model.user.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
  @SerialName("AccessToken") val accessToken: String,
  @SerialName("RefreshToken") val refreshToken: String,
)

internal fun TokenResponse.toModel() = Token(
  accessToken = accessToken,
  refreshToken = refreshToken,
)
