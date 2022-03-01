package com.kunize.uswtimetable.dataclass

data class MyEvaluation (
    val id: String,
    val subject: String,
    val subjectType: String,
    val semester: String,
    val professor: String,
    val totalValue: Float,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Boolean,
    val homework: String,
    val grade: String,
    val content: String
)
