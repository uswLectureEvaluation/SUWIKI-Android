package com.suwiki.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("loginId") val userId: String,
    val email: String,
    val point: Int,
    val writtenEvaluation: Int,
    val writtenExam: Int,
    val viewExam: Int,
)
