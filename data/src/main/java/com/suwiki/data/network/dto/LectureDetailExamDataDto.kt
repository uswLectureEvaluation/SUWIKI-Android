package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName

data class LectureDetailExamDataDto(
    val data: MutableList<LectureDetailExamDto>,
    val examDataExist: Boolean,
    val written: Boolean,
)

data class LectureDetailExamDto(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
)
