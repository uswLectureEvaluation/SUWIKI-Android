package com.suwiki.remote.lectureevaluation.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateExamEvaluationRequest(
  val selectedSemester: String?,
  val examInfo: String,
  val examType: String?,
  val examDifficulty: String,
  val content: String,
)
