package com.suwiki.signup.response

import kotlinx.serialization.Serializable

@Serializable
data class OverlapCheckResponse(
    val overlap: Boolean,
)