package com.kunize.uswtimetable.data.remote

data class LectureExamEditDto(
    val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String
)

data class LectureExamPostDto(
    val lectureName: String,
    val professor: String,
    val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String
)