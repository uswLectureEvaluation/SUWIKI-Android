package com.suwiki.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class OverlapCheckResponse(
    val overlap: Boolean,
)
