package com.suwiki.user.response

import com.suwiki.model.User
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

internal fun UserResponse.toModel() = User(
    userId = userId,
    email = email,
    point = point,
    writtenEvaluation = writtenEvaluation,
    writtenExam = writtenExam,
    viewExam = viewExam,
)