package com.suwiki.remote.request

data class PostLectureExamRequest(
    val lectureName: String?,
    val professor: String?,
    val selectedSemester: String?,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
)
