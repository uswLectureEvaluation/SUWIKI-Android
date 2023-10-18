package com.suwiki.remote.lectureevaluation.viewer.response.lecture

import com.suwiki.core.model.lectureevaluation.LectureDetailEvaluation
import com.suwiki.core.model.lectureevaluation.LectureDetailEvaluationData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureDetailEvaluationDataResponse(
    val data: List<LectureDetailEvaluationResponse>,
    val written: Boolean,
)

internal fun LectureDetailEvaluationDataResponse.toModel() =
    LectureDetailEvaluationData(
        data = data.map { it.toModel() },
        written = written,
    )

@Serializable
data class LectureDetailEvaluationResponse(
    val id: Long,
    @SerialName("selectedSemester") val semester: String,
    val totalAvg: Float,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String,
)

internal fun LectureDetailEvaluationResponse.toModel() =
    LectureDetailEvaluation(
        id = id,
        semester = semester,
        totalAvg = totalAvg,
        satisfaction = satisfaction,
        learning = learning,
        honey = honey,
        team = team,
        difficulty = difficulty,
        homework = homework,
        content = content,
    )
