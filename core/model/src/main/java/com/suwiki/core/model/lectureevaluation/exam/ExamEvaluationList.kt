package com.suwiki.core.model.lectureevaluation.exam

data class ExamEvaluationList(
  val data: List<ExamEvaluation>,
  val needBuyExam: Boolean,
  val written: Boolean,
)

data class ExamEvaluation(
  val id: Long,
  val selectedSemester: String,
  val examInfo: String,
  val examType: String?,
  val examDifficulty: String,
  val content: String,
)
