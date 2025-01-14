package com.suwiki.remote.lectureevaluation.request

import kotlinx.serialization.Serializable

@Serializable
data class PostExamEvaluationRequest(
  val lectureName: String?,
  val professor: String?,
  val selectedSemester: String?,
  val examInfo: String,
  val examType: String?,
  val examDifficulty: String,
  val content: String,
)
