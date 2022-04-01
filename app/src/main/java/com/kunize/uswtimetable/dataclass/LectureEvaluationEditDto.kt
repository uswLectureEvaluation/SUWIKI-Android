package com.kunize.uswtimetable.dataclass

data class LectureEvaluationEditDto(
    val semester: String,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String
)
