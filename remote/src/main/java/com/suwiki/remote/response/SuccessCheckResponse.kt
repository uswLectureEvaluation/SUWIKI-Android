package com.suwiki.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SuccessCheckResponse(
    val success: Boolean,
)
