package com.suwiki.remote.openmajor.response

import kotlinx.serialization.Serializable

@Serializable
data class OpenMajorListResponse(
    val data: List<String>,
)
