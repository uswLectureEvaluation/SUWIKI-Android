package com.suwiki.core.model.lectureevaluation.exam

import androidx.compose.runtime.Stable

@Stable
data class ExamEvaluationList(
  val data: List<ExamEvaluation>,
  val needBuyExam: Boolean,
  val written: Boolean,
)

@Stable
data class ExamEvaluation(
  val id: Long,
  val selectedSemester: String,
  val examInfo: String,
  val examType: String,
  val examDifficulty: String,
  val content: String,
)
