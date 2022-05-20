package com.kunize.uswtimetable.dataclass

data class LoggedInUser(
    val userId: String,
    val point: Int,
    val writtenEvaluation: Int,
    val writtenExam: Int,
    val viewExam: Int,
    val email: String
)