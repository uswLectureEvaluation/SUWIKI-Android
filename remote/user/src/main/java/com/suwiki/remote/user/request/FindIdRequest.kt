package com.suwiki.remote.user.request

import kotlinx.serialization.Serializable

@Serializable
data class FindIdRequest(val email: String)
