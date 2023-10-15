package com.suwiki.user.response

import kotlinx.serialization.Serializable

@Serializable
data class SuccessCheckResponse(
    val success: Boolean,
)