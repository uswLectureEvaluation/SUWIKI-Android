package com.suwiki.remote.signup.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckEmailRequest(val email: String)
