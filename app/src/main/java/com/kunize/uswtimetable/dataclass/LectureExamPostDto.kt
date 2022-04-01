package com.kunize.uswtimetable.dataclass

data class LectureExamPostDto(
    val lectureName: String,
    val professor: String,
    val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String
)
