package com.kunize.uswtimetable.dataclass

data class LoggedInUser(
    val userId: String,
    val token: String,
    val point: Int,
    val writtenLecture: Int,
    val writtenExam: Int,
    val viewExam: Int
)