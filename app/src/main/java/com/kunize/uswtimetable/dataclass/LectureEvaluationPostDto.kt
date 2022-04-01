package com.kunize.uswtimetable.dataclass

data class LectureEvaluationPostDto(
    val lectureName: String,
    val professor: String,
    val semester: String,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String
)
