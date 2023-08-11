package com.suwiki.remote.request.evaluation

import kotlinx.serialization.Serializable

@Serializable
data class UpdateLectureEvaluationRequest(
    val selectedSemester: String,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String,
)
