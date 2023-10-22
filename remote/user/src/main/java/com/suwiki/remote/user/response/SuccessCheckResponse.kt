package com.suwiki.remote.user.response

import kotlinx.serialization.Serializable

@Serializable
data class SuccessCheckResponse(
  val success: Boolean,
)
