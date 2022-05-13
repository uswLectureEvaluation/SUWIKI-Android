package com.kunize.uswtimetable.data.remote

data class LectureEvaluationEditDto(
    val selectedSemester: String,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String
)

data class LectureEvaluationPostDto(
    val lectureName: String,
    val professor: String,
    val selectedSemester: String,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String
)