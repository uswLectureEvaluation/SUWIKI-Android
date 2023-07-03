package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName

data class LectureDetailEvaluationDataDto(
    val data: MutableList<LectureDetailEvaluationDto>,
    val written: Boolean,
)

data class LectureDetailEvaluationDto(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val totalAvg: Float,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String,
)
