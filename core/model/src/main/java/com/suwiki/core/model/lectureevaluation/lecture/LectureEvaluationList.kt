package com.suwiki.core.model.lectureevaluation.lecture

import androidx.compose.runtime.Stable

@Stable
data class LectureEvaluationList(
  val data: List<LectureEvaluation>,
  val written: Boolean,
)

@Stable
data class LectureEvaluation(
  val id: Long,
  val selectedSemester: String,
  val totalAvg: Float,
  val satisfaction: Float,
  val learning: Float,
  val honey: Float,
  val team: Int,
  val difficulty: Int,
  val homework: Int,
  val content: String,
)
