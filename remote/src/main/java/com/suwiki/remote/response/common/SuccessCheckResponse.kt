package com.suwiki.remote.response.common

import kotlinx.serialization.Serializable

@Serializable
data class SuccessCheckResponse(
    val success: Boolean,
)
