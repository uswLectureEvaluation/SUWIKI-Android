package com.suwiki.remote.login.response

import kotlinx.serialization.Serializable

@Serializable
data class SuccessCheckResponse(
  val success: Boolean,
)
