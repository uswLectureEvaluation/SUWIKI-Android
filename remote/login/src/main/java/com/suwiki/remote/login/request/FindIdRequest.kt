package com.suwiki.remote.login.request

import kotlinx.serialization.Serializable

@Serializable
data class FindIdRequest(val email: String)
