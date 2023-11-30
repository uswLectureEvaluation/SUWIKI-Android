package com.suwiki.remote.signup.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckIdRequest(val loginId: String)
