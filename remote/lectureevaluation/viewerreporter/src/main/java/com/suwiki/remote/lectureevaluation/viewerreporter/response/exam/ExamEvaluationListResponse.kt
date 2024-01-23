package com.suwiki.remote.lectureevaluation.viewerreporter.response.exam

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluation
import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList
import kotlinx.serialization.Serializable

@Serializable
data class ExamEvaluationListResponse(
  val data: List<ExamEvaluationResponse>,
  val examDataExist: Boolean,
  val written: Boolean,
)

internal fun ExamEvaluationListResponse.toModel() = ExamEvaluationList(
  data = data.map { it.toModel() },
  needBuyExam = examDataExist && data.isEmpty(),
  written = written,
)

@Serializable
data class ExamEvaluationResponse(
  val id: Long,
  val selectedSemester: String,
  val examInfo: String,
  val examType: String = "",
  val examDifficulty: String,
  val content: String,
)

internal fun ExamEvaluationResponse.toModel() = ExamEvaluation(
  id = id,
  selectedSemester = selectedSemester,
  examInfo = examInfo,
  examType = examType,
  examDifficulty = examDifficulty,
  content = content,
)
