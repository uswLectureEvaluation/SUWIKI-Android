package com.suwiki.remote.response.user

import kotlinx.serialization.Serializable

@Serializable
data class OverlapCheckResponse(
    val overlap: Boolean,
)
