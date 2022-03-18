package com.kunize.uswtimetable.dataclass

import java.io.Serializable

data class ExamPost(
    val id: Long,
    val lectureName: String,
    val professor: String,
    val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String
): Serializable
