package com.suwiki.remote.user.response

import com.suwiki.core.model.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
  @SerialName("loginId") val userId: String,
  val email: String,
  val point: Int = 0,
  val writtenEvaluation: Int = 0,
  val writtenExam: Int = 0,
  val viewExam: Int = 0,
)

internal fun UserResponse.toModel() = User(
  userId = userId,
  email = email,
  point = point,
  writtenEvaluation = writtenEvaluation,
  writtenExam = writtenExam,
  viewExam = viewExam,
)
