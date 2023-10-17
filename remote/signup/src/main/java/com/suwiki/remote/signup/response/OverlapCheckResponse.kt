package com.suwiki.remote.signup.response

import kotlinx.serialization.Serializable

@Serializable
data class OverlapCheckResponse(
    val overlap: Boolean,
)