package com.suwiki.remote.response.major

import kotlinx.serialization.Serializable

@Serializable
data class OpenMajorVersionResponse(
    val version: Float,
)
