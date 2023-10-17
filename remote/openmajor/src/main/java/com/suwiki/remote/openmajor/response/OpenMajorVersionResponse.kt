package com.suwiki.remote.openmajor.response

import kotlinx.serialization.Serializable

@Serializable
data class OpenMajorVersionResponse(
    val version: Float,
)
