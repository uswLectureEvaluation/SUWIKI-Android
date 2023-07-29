package com.suwiki.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class OpenMajorVersionResponse(
    val version: Float,
)
