package com.suwiki.core.model

data class LectureDetailExam(
    val id: Long,
    val semester: String,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
)
