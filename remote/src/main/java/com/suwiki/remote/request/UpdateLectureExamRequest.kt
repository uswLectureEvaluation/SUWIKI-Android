package com.suwiki.remote.request

data class UpdateLectureExamRequest(
    val selectedSemester: String?,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
)
