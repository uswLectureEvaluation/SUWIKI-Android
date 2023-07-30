package com.suwiki.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureDetailEvaluationDataResponse(
    val data: List<LectureDetailEvaluationResponse>,
    val written: Boolean,
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
