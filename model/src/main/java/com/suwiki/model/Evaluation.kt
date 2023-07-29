package com.suwiki.model

data class Evaluation(
    val name: String = "",
    val professor: String = "",
    val aver: Float = 0f,
    val satisfaction: Float = 0f,
    val honey: Float = 0f,
    val learning: Float = 0f,
    val type: String = "",
    val yearSemester: String = "",
    val teamMeeting: Int = 0,
    val task: Int = 0,
    val grade: Int = 0,
    val content: String? = "내용이 없습니다.",
)

data class Exam(
    val content: String? = "내용이 없습니다.",
    val difficulty: String = "",
    val testMethod: String = "",
    val lectureId: Long = 0,
    val majorType: String = "",
    val examType: String? = "",
)
