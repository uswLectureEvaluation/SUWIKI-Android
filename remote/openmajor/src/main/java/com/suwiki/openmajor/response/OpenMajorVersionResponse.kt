package com.suwiki.openmajor.response

import kotlinx.serialization.Serializable

@Serializable
data class OpenMajorVersionResponse(
    val version: Float,
)
