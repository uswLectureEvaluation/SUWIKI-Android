package com.suwiki.remote.lectureevaluation.response

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationList
import kotlinx.serialization.Serializable

@Serializable
data class LectureEvaluationListResponse(
  val data: List<LectureEvaluationResponse>,
  val written: Boolean,
)

internal fun LectureEvaluationListResponse.toModel() =
  LectureEvaluationList(
    data = data.map { it.toModel() },
    written = written,
  )

@Serializable
data class LectureEvaluationResponse(
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

internal fun LectureEvaluationResponse.toModel() =
  LectureEvaluation(
    id = id,
    selectedSemester = selectedSemester,
    totalAvg = totalAvg,
    satisfaction = satisfaction,
    learning = learning,
    honey = honey,
    team = team,
    difficulty = difficulty,
    homework = homework,
    content = content,
  )
