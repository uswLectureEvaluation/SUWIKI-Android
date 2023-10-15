package com.suwiki.signup.response

import kotlinx.serialization.Serializable

@Serializable
data class SuccessCheckResponse(
    val success: Boolean,
)